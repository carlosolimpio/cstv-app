package com.carlosolimpio.cstv.data.matchdetails.remote

import com.google.gson.annotations.SerializedName

data class TeamPlayersDto(
    val id: Long,
    val players: List<PlayerDto>
)

data class PlayerDto(
    @SerializedName("name") val nickname: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("image_url") val imageUrl: String
)
