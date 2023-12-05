package com.carlosolimpio.cstv.domain.mainlist

import com.carlosolimpio.cstv.data.common.DataResponse
import kotlinx.coroutines.flow.Flow

interface MainListRepository {

    suspend fun fetchMainList(): Flow<DataResponse<List<Match>>>
}
