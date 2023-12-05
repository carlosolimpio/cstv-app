package com.carlosolimpio.cstv.data.mainlist.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosolimpio.cstv.data.mainlist.NETWORK_PAGE_SIZE
import com.carlosolimpio.cstv.data.mainlist.mapToMatch
import com.carlosolimpio.cstv.data.mainlist.remote.MainListService
import com.carlosolimpio.cstv.domain.mainlist.Match
import java.io.IOException
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

class MatchesPagingSource(
    private val apiService: MainListService
) : PagingSource<Int, Match>() {
    override fun getRefreshKey(state: PagingState<Int, Match>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Match> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiService.getUpcomingMatchesList(
                pageIndex = pageIndex,
                pageSize = params.loadSize
            )
            val matches = response.body() ?: emptyList()

            LoadResult.Page(
                data = matches.mapToMatch(),
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (matches.isNotEmpty()) {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                } else {
                    null
                }
            )
        } catch (ioe: IOException) {
            return LoadResult.Error(ioe)
        } catch (he: HttpException) {
            return LoadResult.Error(he)
        }
    }
}
