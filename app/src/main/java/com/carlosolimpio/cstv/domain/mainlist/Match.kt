package com.carlosolimpio.cstv.domain.mainlist

data class Match(
    val status: MatchStatus,
    val matchTime: String,
    val league: League,
    val serieName: String,
    val teamA: Team,
    val teamB: Team
)

data class League(
    val name: String,
    val imageUrl: String
)

data class Team(
    val id: Long,
    val name: String,
    val imageUrl: String
)

enum class MatchStatus {
    CANCELED,
    FINISHED,
    NOT_STARTED,
    POSTPONED,
    RUNNING
}
