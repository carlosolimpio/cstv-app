package com.carlosolimpio.cstv.presentation.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosolimpio.cstv.data.common.DataResponse
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val repository: MainListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Match>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Match>>> = _uiState

    fun fetchMainList() {
        viewModelScope.launch {
            repository.fetchMainList().collectLatest { response ->
                _uiState.value = when (response) {
                    is DataResponse.Error -> UiState.Error(response.message)
                    is DataResponse.Success -> UiState.Success(response.data)
                }
            }
        }
    }
}
