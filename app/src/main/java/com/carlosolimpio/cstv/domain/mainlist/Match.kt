package com.carlosolimpio.cstv.domain.mainlist

import kotlinx.serialization.Serializable

@Serializable
data class Match(
    val id: Long,
    val status: MatchStatus,
    val matchTime: String,
    val league: League,
    val serieName: String,
    val teamA: Team,
    val teamB: Team
)

@Serializable
data class League(
    val name: String,
    val imageUrl: String
)

@Serializable
data class Team(
    val id: Long,
    val name: String,
    val imageUrl: String
)

@Serializable
enum class MatchStatus {
    CANCELED,
    FINISHED,
    NOT_STARTED,
    POSTPONED,
    RUNNING
}
