package com.carlosolimpio.cstv.data.mainlist

import com.carlosolimpio.cstv.data.mainlist.remote.MatchDto
import com.carlosolimpio.cstv.domain.mainlist.League
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.domain.mainlist.Team

fun List<MatchDto>.mapToMatch() = this.map { it.toMatch() }.filter { it.isValidMatch() }

fun Match.isValidMatch(): Boolean {
    return matchTime.isNotBlank() && teamA.name.isNotBlank() && teamB.name.isNotBlank()
}

fun MatchDto.toMatch(): Match {
    val teamA = teams.getOrNull(0)
    val teamB = teams.getOrNull(1)

    return Match(
        id = id,
        status = enumValueOf(status.uppercase()),
        matchTime = matchTime ?: "",
        league = League(
            name = league.name,
            imageUrl = league.imageUrl ?: ""
        ),
        serieName = serie.name,
        teamA = Team(
            id = teamA?.team?.id ?: 0L,
            name = teamA?.team?.name ?: "",
            imageUrl = teamA?.team?.imageUrl ?: ""
        ),
        teamB = Team(
            id = teamB?.team?.id ?: 0L,
            name = teamB?.team?.name ?: "",
            imageUrl = teamB?.team?.imageUrl ?: ""
        )
    )
}
