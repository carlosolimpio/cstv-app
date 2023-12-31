package com.carlosolimpio.cstv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carlosolimpio.cstv.domain.common.DataResponse
import com.carlosolimpio.cstv.domain.mainlist.League
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.domain.mainlist.MatchStatus
import com.carlosolimpio.cstv.domain.mainlist.Team
import com.carlosolimpio.cstv.domain.matchdetails.Player
import com.carlosolimpio.cstv.domain.matchdetails.TeamPlayers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
open class BaseUnitTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }

    protected val errorResponse = DataResponse.Error("Custom test error message")

    protected val matchInput = Match(
        id = 98765,
        status = MatchStatus.NOT_STARTED,
        matchTime = "",
        league = League("Major Rio", "league/url"),
        serieName = "2023",
        teamA = Team(
            id = 12345,
            name = "Furia",
            imageUrl = "team/url"
        ),
        teamB = Team(
            id = 54321,
            name = "Luminosity",
            imageUrl = "team/url"
        )
    )
    protected val matchDetailsResponse = DataResponse.Success(
        Pair(
            first = TeamPlayers(
                teamId = 12345,
                players = listOf(
                    Player("chelo", "Marcelo Cespedes", "profile/pic/url"),
                    Player("chelo", "Marcelo Cespedes", "profile/pic/url"),
                    Player("chelo", "Marcelo Cespedes", "profile/pic/url"),
                    Player("chelo", "Marcelo Cespedes", "profile/pic/url"),
                    Player("chelo", "Marcelo Cespedes", "profile/pic/url")
                )
            ),
            second = TeamPlayers(
                teamId = 54321,
                players = listOf(
                    Player("FalleN", "Gabriel Toledo", "profile/pic/url"),
                    Player("FalleN", "Gabriel Toledo", "profile/pic/url"),
                    Player("FalleN", "Gabriel Toledo", "profile/pic/url"),
                    Player("FalleN", "Gabriel Toledo", "profile/pic/url"),
                    Player("FalleN", "Gabriel Toledo", "profile/pic/url")
                )
            )
        )
    )

    /*protected val mainListResponse = DataResponse.Success(
        listOf(
            Match(
                MatchStatus.NOT_STARTED,
                "2023-12-05T09:00:00Z",
                League("United21", "image.url.example"),
                "Season 9 2023",
                Team(1, "Furia", "image.url.example"),
                Team(2, "MiBR", "image.url.example")
            ),
            Match(
                MatchStatus.NOT_STARTED,
                "2023-12-05T09:00:00Z",
                League("United21", "image.url.example"),
                "Season 9 2023",
                Team(1, "Imperial", "image.url.example"),
                Team(2, "NaVi", "image.url.example")
            ),
            Match(
                MatchStatus.RUNNING,
                "2023-12-05T09:00:00Z",
                League("United21", "image.url.example"),
                "Season 9 2023",
                Team(1, "Furia", "image.url.example"),
                Team(2, "NaVi", "image.url.example")
            ),
            Match(
                MatchStatus.RUNNING,
                "2023-12-05T09:00:00Z",
                League("United21", "image.url.example"),
                "Season 9 2023",
                Team(1, "Furia", "image.url.example"),
                Team(2, "FaZe", "image.url.example")
            ),
        )
    )*/
}
