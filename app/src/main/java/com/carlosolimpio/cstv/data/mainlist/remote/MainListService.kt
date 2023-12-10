package com.carlosolimpio.cstv.data.mainlist.remote

import com.carlosolimpio.cstv.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainListService {
    @GET("matches?token=${BuildConfig.PANDASCORE_TOKEN}")
    suspend fun getUpcomingMatchesList(
        @Query("filter[status]") filter: String = "running,not_started",
        @Query("sort") sortedBy: String = "-status,begin_at",
        @Query("page") pageIndex: Int,
        @Query("per_page") pageSize: Int
    ): Response<List<MatchDto>>
}
