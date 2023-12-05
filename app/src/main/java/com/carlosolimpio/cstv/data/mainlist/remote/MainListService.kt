package com.carlosolimpio.cstv.data.mainlist.remote

import com.carlosolimpio.cstv.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

interface MainListService {
    @GET("matches/running?token=${BuildConfig.PANDASCORE_TOKEN}")
    suspend fun getRunningMatchesList(): Response<List<MatchDto>>

    @GET("matches/upcoming?token=${BuildConfig.PANDASCORE_TOKEN}")
    suspend fun getUpcomingMatchesList(): Response<List<MatchDto>>
}
