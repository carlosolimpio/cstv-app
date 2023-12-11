package com.carlosolimpio.cstv.domain.matchdetails

import com.carlosolimpio.cstv.domain.common.DataResponse
import kotlinx.coroutines.flow.Flow

interface MatchDetailsRepository {
    suspend fun fetchPlayers(teamAId: Long, teamBId: Long): Flow<DataResponse<Pair<TeamPlayers, TeamPlayers>>>
}