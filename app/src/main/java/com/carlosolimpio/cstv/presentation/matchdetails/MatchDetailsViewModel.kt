package com.carlosolimpio.cstv.presentation.matchdetails

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.carlosolimpio.cstv.domain.common.DataResponse
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.domain.matchdetails.MatchDetail
import com.carlosolimpio.cstv.domain.matchdetails.MatchDetailsRepository
import com.carlosolimpio.cstv.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MatchDetailsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchDetail>> get() = _uiState

    fun fetchMatchDetails(match: Match) {
        val matchDetails: MatchDetail? = savedStateHandle.get<String>(MATCH_KEY)?.let {
            Json.decodeFromString<MatchDetail>(it)
        }

        if (matchDetails != null) {
            _uiState.value = UiState.Success(matchDetails)
        } else {
            viewModelScope.launch {
                repository.fetchPlayers(match.teamA.id, match.teamB.id).collect {
                    _uiState.value = when (it) {
                        is DataResponse.Success -> {
                            UiState.Success(
                                MatchDetail(
                                    match = match,
                                    teamPlayers = it.data
                                )

                            )
                        }
                        is DataResponse.Error -> UiState.Error(it.message)
                    }
                }
            }
        }
    }

    fun saveMatchToState(matchDetails: MatchDetail) {
        savedStateHandle[MATCH_KEY] = Json.encodeToString(matchDetails)
    }

    companion object {
        private const val MATCH_KEY = "match_key"
    }
}
