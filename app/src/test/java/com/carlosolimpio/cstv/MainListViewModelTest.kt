package com.carlosolimpio.cstv

import app.cash.turbine.test
import com.carlosolimpio.cstv.domain.mainlist.MainListRepository
import com.carlosolimpio.cstv.presentation.common.UiState
import com.carlosolimpio.cstv.presentation.mainlist.MainListViewModel
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
class MainListViewModelTest : BaseUnitTest() {

    @MockK
    private lateinit var repository: MainListRepository

    private lateinit var viewModel: MainListViewModel

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        viewModel = MainListViewModel(repository)
    }

    @Test
    fun `test when fetching the main matches list, the loading state is shown before success`() = runTest {
        coEvery { repository.fetchMainList() } returns flowOf(mainListResponse)

        viewModel.fetchMainList()

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when fetching the main matches list, show success state with a valid list`() = runTest {
        coEvery { repository.fetchMainList() } returns flowOf(mainListResponse)

        viewModel.fetchMainList()

        viewModel.uiState.test {
            awaitItem() // Loading state

            val successItem = awaitItem()
            assertTrue(successItem is UiState.Success)
            assertTrue((successItem as UiState.Success).data.isNotEmpty())
        }
    }

    @Test
    fun `test when fetching the main matches list, if an error occur show error state with its message`() = runTest {
        coEvery { repository.fetchMainList() } returns flowOf(errorResponse)

        viewModel.fetchMainList()

        viewModel.uiState.test {
            awaitItem() // Loading state

            val errorItem = awaitItem()
            assertTrue(errorItem is UiState.Error)
            assertEquals(errorResponse.message, (errorItem as UiState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // TBD test if the running matches are being on beginning of the list
    // TBD test if the list is correctly sorted by the date

    //    @Test
    //    fun ``() = runTest {
    //
    //    }
}
