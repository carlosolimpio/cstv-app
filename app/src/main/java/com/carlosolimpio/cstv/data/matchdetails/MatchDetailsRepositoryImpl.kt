package com.carlosolimpio.cstv.data.matchdetails

import com.carlosolimpio.cstv.data.common.handleResponse
import com.carlosolimpio.cstv.data.matchdetails.remote.MatchDetailsService
import com.carlosolimpio.cstv.domain.common.DataResponse
import com.carlosolimpio.cstv.domain.matchdetails.MatchDetailsRepository
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchDetailsRepositoryImpl @Inject constructor(
    private val apiService: MatchDetailsService
) : MatchDetailsRepository {

    override suspend fun fetchPlayers(teamAId: Long, teamBId: Long) = flow {
        try {
            apiService.getPlayersInfo("$teamAId,$teamBId")
                .handleResponse(
                    onSuccess = { teamPlayers ->
                        val (teamA, teamB) = if (teamPlayers[0].toTeamPlayers().teamId == teamAId) {
                            Pair(teamPlayers[0].toTeamPlayers(), teamPlayers[1].toTeamPlayers())
                        } else {
                            Pair(teamPlayers[1].toTeamPlayers(), teamPlayers[0].toTeamPlayers())
                        }

                        emit(
                            DataResponse.Success(
                                Pair(teamA, teamB)
                            )
                        )
                    },
                    onError = {
                        emit(DataResponse.Error(it))
                    }
                )
        } catch (uh: UnknownHostException) {
            emit(DataResponse.Error("Device not connected to internet"))
        } catch (e: Exception) {
            emit(DataResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
