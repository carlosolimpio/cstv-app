package com.carlosolimpio.cstv.domain.mainlist

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MainListRepository {
    suspend fun fetchMainList(): Flow<PagingData<Match>>
}
