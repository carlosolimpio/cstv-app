package com.carlosolimpio.cstv.presentation.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.carlosolimpio.cstv.data.common.DataResponse
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val repository: MainListRepository
) : ViewModel() {

    /*private val _uiState = MutableStateFlow<UiState<List<Match>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Match>>> = _uiState*/

    suspend fun fetchMainList() = repository.fetchMainList().cachedIn(viewModelScope)
}
