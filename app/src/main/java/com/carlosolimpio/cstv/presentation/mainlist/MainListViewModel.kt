package com.carlosolimpio.cstv.presentation.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val repository: MainListRepository
) : ViewModel() {
    suspend fun fetchMainList() = repository.fetchMainList().cachedIn(viewModelScope)
}
