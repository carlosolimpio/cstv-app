package com.carlosolimpio.cstv.domain.matchdetails

data class TeamPlayers(
    val teamId: Long,
    val players: List<Player>
)

data class Player(
    val nickName: String,
    val realName: String,
    val profilePictureUrl: String
)
