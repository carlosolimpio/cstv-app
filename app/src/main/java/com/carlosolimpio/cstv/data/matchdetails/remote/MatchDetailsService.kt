package com.carlosolimpio.cstv.data.matchdetails.remote

import com.carlosolimpio.cstv.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchDetailsService {
    @GET("teams?token=${BuildConfig.PANDASCORE_TOKEN}")
    suspend fun getPlayersInfo(
        @Query("filter[id]") ids: String
    ): Response<List<TeamPlayersDto>>
}
