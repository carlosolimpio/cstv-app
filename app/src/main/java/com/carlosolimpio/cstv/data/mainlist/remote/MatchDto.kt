package com.carlosolimpio.cstv.data.mainlist.remote

import com.google.gson.annotations.SerializedName

data class MatchDto(
    val status: String,
    @SerializedName("begin_at") val matchTime: String,
    @SerializedName("league") val league: LeagueDto,
    @SerializedName("serie") val serie: SerieDto,
    @SerializedName("opponents") val teams: List<TeamDataDto>
)

data class LeagueDto(
    val name: String,
    @SerializedName("image_url") val imageUrl: String
)

data class SerieDto(
    @SerializedName("full_name") val name: String
)

data class TeamDataDto(
    @SerializedName("opponent") val team: TeamDto
)

data class TeamDto(
    val id: Long,
    val name: String,
    @SerializedName("image_url") val imageUrl: String
)
