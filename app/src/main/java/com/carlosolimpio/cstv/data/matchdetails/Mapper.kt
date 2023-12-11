package com.carlosolimpio.cstv.data.matchdetails

import com.carlosolimpio.cstv.data.matchdetails.remote.PlayerDto
import com.carlosolimpio.cstv.data.matchdetails.remote.TeamPlayersDto
import com.carlosolimpio.cstv.domain.matchdetails.Player
import com.carlosolimpio.cstv.domain.matchdetails.TeamPlayers

fun TeamPlayersDto.toTeamPlayers() = TeamPlayers(
    teamId = id,
    players = players.toPlayerList()
)

fun List<PlayerDto>.toPlayerList() = this.map { it.toPlayer() }

fun PlayerDto.toPlayer() = Player(
    nickName = nickname ?: "",
    realName = "${firstName ?: ""} ${lastName ?: ""}",
    profilePictureUrl = imageUrl ?: ""
)
