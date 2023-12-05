package com.carlosolimpio.cstv.data.mainlist

import com.carlosolimpio.cstv.data.common.DataResponse
import com.carlosolimpio.cstv.data.common.handleResponse
import com.carlosolimpio.cstv.data.mainlist.remote.MainListService
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainListRepositoryImpl @Inject constructor(
    private val apiService: MainListService
) : MainListRepository {
    override suspend fun fetchMainList() = flow {
        apiService.getRunningMatchesList().handleResponse(
            onSuccess = {
                emit(DataResponse.Success(it.mapMatches()))
            },
            onError = {
                emit(DataResponse.Error(it))
            }
        )

        apiService.getUpcomingMatchesList().handleResponse(
            onSuccess = {
                emit(DataResponse.Success(it.mapMatches()))
            },
            onError = {
                emit(DataResponse.Error(it))
            }
        )
    }.flowOn(Dispatchers.IO)
}
