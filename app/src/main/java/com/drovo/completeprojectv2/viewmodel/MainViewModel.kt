package com.drovo.completeprojectv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.drovo.completeprojectv2.data.Dog
import com.drovo.completeprojectv2.network.ApiService
import com.drovo.completeprojectv2.repository.DogPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {
    //config = PagingConfig(20, enablePlaceholders = false)
    //PagingConfig(at a time ki poriman data load korate hobe, )

    //flow.cachedIn(viewModelScope)
    //configuration change korleo viewmodel data loss korbe na...dhore rakhbe
    fun getAllDogs(): Flow<PagingData<Dog>> = Pager(
        config = PagingConfig(20, enablePlaceholders = false)
    ){
        DogPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)
}