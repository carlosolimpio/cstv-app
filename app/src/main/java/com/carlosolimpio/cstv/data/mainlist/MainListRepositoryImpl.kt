package com.carlosolimpio.cstv.data.mainlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosolimpio.cstv.data.mainlist.remote.MainListService
import com.carlosolimpio.cstv.data.mainlist.remote.paging.MatchesPagingSource
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import com.carlosolimpio.cstv.domain.mainlist.Match
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

const val NETWORK_PAGE_SIZE = 10

class MainListRepositoryImpl @Inject constructor(
    private val apiService: MainListService
) : MainListRepository {
    override suspend fun fetchMainList(): Flow<PagingData<Match>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MatchesPagingSource(apiService) }
        ).flow.flowOn(Dispatchers.IO)
    }
}
