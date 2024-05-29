package com.example.rickandmortywiki.presentation.locationScreen

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortywiki.api.Api
import com.example.rickandmortywiki.api.LocationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationPagingSource(
    private val api: Api,
    private val throwable: MutableLiveData<Throwable?>
) : PagingSource<Int, LocationItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationItem> {
        return try {
            val page = params.key ?: 1
            val response = withContext(Dispatchers.IO) {
                api.loadLocationList(page = page).execute()
            }

            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                throwable.postValue(Exception("Failed to load data"))
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            throwable.postValue(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

