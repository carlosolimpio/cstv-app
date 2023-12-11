package com.carlosolimpio.cstv.domain.matchdetails

import kotlinx.serialization.Serializable

@Serializable
data class TeamPlayers(
    val teamId: Long,
    val players: List<Player>
)

@Serializable
data class Player(
    val nickName: String,
    val realName: String,
    val profilePictureUrl: String
)
