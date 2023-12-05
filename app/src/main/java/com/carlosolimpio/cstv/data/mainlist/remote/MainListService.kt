package com.carlosolimpio.cstv.data.mainlist.remote

import com.carlosolimpio.cstv.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainListService {
    @GET("matches/running?token=${BuildConfig.PANDASCORE_TOKEN}")
    suspend fun getRunningMatchesList(): Response<List<MatchDto>>

    @GET("matches/upcoming?token=${BuildConfig.PANDASCORE_TOKEN}")
    suspend fun getUpcomingMatchesList(
        @Query("page") pageIndex: Int,
        @Query("per_page") pageSize: Int
    ): Response<List<MatchDto>>
}