package com.example.rickandmortywiki.presentation.paginationScreen

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortywiki.api.Api
import com.example.rickandmortywiki.api.Character
import com.example.rickandmortywiki.presentation.characterDetailScreen.CharacterPagingSource
import kotlinx.coroutines.flow.Flow

class PaginationViewModel : ViewModel() {
    private val _throwable = MutableLiveData<Throwable?>()
    val throwable: LiveData<Throwable?> get() = _throwable

    fun setThrowable() {
        _throwable.value = null
    }

    val pagingData: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { CharacterPagingSource(Api.retrofit, _throwable) }
    ).flow.cachedIn(viewModelScope)
}