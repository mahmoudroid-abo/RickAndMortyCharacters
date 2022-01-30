package com.mahmoudroid.rickmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mahmoudroid.rickmorty.data.ApiService
import com.mahmoudroid.rickmorty.data.CharactersPagingSource
import com.mahmoudroid.rickmorty.data.RetrofitInstance
import com.mahmoudroid.rickmorty.model.Characters
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel : ViewModel() {

    lateinit var apiService: ApiService

    init {
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
    }

    fun getCharactersList(): Flow<PagingData<Characters>> {

        return Pager(
            config = PagingConfig(pageSize = 42, maxSize = 200),
            pagingSourceFactory = { CharactersPagingSource(apiService) }
        ).flow.cachedIn(viewModelScope)
    }
}
