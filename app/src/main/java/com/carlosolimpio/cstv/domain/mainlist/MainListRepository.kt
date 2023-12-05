package com.carlosolimpio.cstv.domain.mainlist

import androidx.paging.PagingData
import com.carlosolimpio.cstv.data.common.DataResponse
import kotlinx.coroutines.flow.Flow

interface MainListRepository {

    suspend fun fetchMainList(): Flow<PagingData<Match>>
}
