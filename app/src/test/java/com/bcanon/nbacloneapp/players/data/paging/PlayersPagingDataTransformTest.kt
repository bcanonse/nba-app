package com.bcanon.nbacloneapp.players.data.paging

import androidx.paging.AsyncPagingDataDiffer
import com.bcanon.nbacloneapp.core.DiffCallback
import com.bcanon.nbacloneapp.players.domain.model.Players
import com.bcanon.nbacloneapp.players.domain.model.mockPagingDataPlayers
import com.bcanon.nbacloneapp.players.domain.model.playersList
import com.bcanon.nbacloneapp.utils.NoopListCallback
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayersPagingDataTransformTest {
    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Differ transforms data paging to list players`() = testScope.runTest {
        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffCallback<Players>(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        // You don't need to use lifecycleScope.launch() if you're using
        // PagingData.from()
        differ.submitData(mockPagingDataPlayers)

        // Wait for transforms and the differ to process all updates.
        advanceUntilIdle()
        assertEquals(playersList, differ.snapshot().items)
    }
}


