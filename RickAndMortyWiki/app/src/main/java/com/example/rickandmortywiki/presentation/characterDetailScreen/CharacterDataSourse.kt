package com.example.rickandmortywiki.presentation.characterDetailScreen


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortywiki.api.Api
import com.example.rickandmortywiki.api.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CharacterPagingSource(private val api: Api, private val throwable: MutableLiveData<Throwable?>) : PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1

            val response = withContext(Dispatchers.IO) {
                api.loadList(page = page).execute()
            }

            if (response.isSuccessful) {
                val characters = response.body()?.results ?: emptyList()

                Log.d("@@@name",characters[0].name)
                LoadResult.Page(
                    data = characters,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (characters.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            throwable.postValue(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }
}

