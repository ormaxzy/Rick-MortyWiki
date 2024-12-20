package com.example.rickandmortywiki.presentation.locationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortywiki.api.Api
import com.example.rickandmortywiki.api.LocationItem
import kotlinx.coroutines.flow.Flow

class LocationViewModel : ViewModel() {
    private val _throwable = MutableLiveData<Throwable?>()
    val throwable: LiveData<Throwable?> get() = _throwable

    fun setThrowable() {
        _throwable.value = null
    }

    private val pagingSource = LocationPagingSource(Api.retrofit, _throwable)

    val pagingData: Flow<PagingData<LocationItem>> = Pager(
        config = PagingConfig(pageSize = Api.PAGE_SIZE),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)
}