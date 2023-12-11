package com.carlosolimpio.cstv.domain.matchdetails

import com.carlosolimpio.cstv.domain.mainlist.Match

data class MatchDetail(
    val match: Match,
    val teamPlayers: Pair<TeamPlayers, TeamPlayers>
)
