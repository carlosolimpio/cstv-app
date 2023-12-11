package com.carlosolimpio.cstv

import app.cash.turbine.test
import com.carlosolimpio.cstv.domain.matchdetails.MatchDetailsRepository
import com.carlosolimpio.cstv.presentation.common.UiState
import com.carlosolimpio.cstv.presentation.mainlist.MainListViewModel
import com.carlosolimpio.cstv.presentation.matchdetails.MatchDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class MatchDetailsViewModelTest : BaseUnitTest() {
    @MockK
    private lateinit var repository: MatchDetailsRepository
    private lateinit var viewModel: MatchDetailsViewModel

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        viewModel = MatchDetailsViewModel(repository)
    }

    @Test
    fun `test when fetching the match details, the loading state is shown before success`() = runTest {
        coEvery { repository.fetchPlayers(12345, 54321) } returns flowOf(matchDetailsResponse)

        viewModel.fetchMatchDetails(matchInput)

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when fetching the match details, show success state with valid match details`() = runTest {
        coEvery { repository.fetchPlayers(12345, 54321) } returns flowOf(matchDetailsResponse)

        viewModel.fetchMatchDetails(matchInput)

        viewModel.uiState.test {
            awaitItem() // Loading state

            val successItem = awaitItem()
            assertTrue(successItem is UiState.Success)
            assertTrue((successItem as UiState.Success).data.match.teamA.name.isNotBlank())
            assertTrue(successItem.data.match.teamB.name.isNotBlank())
            assertTrue(successItem.data.teamPlayers.first.players.isNotEmpty())
            assertTrue(successItem.data.teamPlayers.second.players.isNotEmpty())
        }
    }

    @Test
    fun `test when fetching the match details, if an error occur show error state with its message`() = runTest {
        coEvery { repository.fetchPlayers(12345, 54321) } returns flowOf(errorResponse)

        viewModel.fetchMatchDetails(matchInput)

        viewModel.uiState.test {
            awaitItem() // Loading state

            val errorItem = awaitItem()
            assertTrue(errorItem is UiState.Error)
            assertEquals(errorResponse.message, (errorItem as UiState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}