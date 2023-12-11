package com.carlosolimpio.cstv.data.matchdetails

import com.carlosolimpio.cstv.data.matchdetails.remote.PlayerDto
import com.carlosolimpio.cstv.data.matchdetails.remote.TeamPlayersDto
import com.carlosolimpio.cstv.domain.matchdetails.Player
import com.carlosolimpio.cstv.domain.matchdetails.TeamPlayers

fun TeamPlayersDto.toTeamPlayers() = TeamPlayers(
    teamId = id,
    players = players.toPlayerList()
)

fun List<PlayerDto>.toPlayerList() = this.fillLineUp().map { it.toPlayer() }

fun List<PlayerDto>.fillLineUp(): List<PlayerDto> {
    val FULL_LINEUP = 5
    if (this.size < FULL_LINEUP) {
        val playersCount = FULL_LINEUP - this.size
        val additionalPlayers = fillWithDummyPlayers(playersCount)
        return this + additionalPlayers
    }
    return this
}

private fun fillWithDummyPlayers(count: Int): List<PlayerDto> {
    val additionalPlayers = mutableListOf<PlayerDto>()
    for (i in 1..count) {
        additionalPlayers.add(PlayerDto("No Info", "No Info", "", ""))
    }
    return additionalPlayers
}

fun PlayerDto.toPlayer() = Player(
    nickName = nickname ?: "",
    realName = "${firstName ?: ""} ${lastName ?: ""}",
    profilePictureUrl = imageUrl ?: ""
)
