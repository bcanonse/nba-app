package com.bcanon.nbacloneapp.players.domain.usecase

import com.bcanon.nbacloneapp.players.data.repositories.PlayersRepository
import com.bcanon.nbacloneapp.players.domain.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPlayersUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: PlayersRepository

    private lateinit var useCase: GetPlayersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPlayersUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when the return something then get paging list players since query and paging from api`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getPlayers(query = any()) } returns mockPagingPlayers
            //When
            val response = useCase(query = "James")
            //Then
            assert(response.count() > 0)
            assert(response.first() == mockPagingDataPlayers)
            coVerify(exactly = 1) { useCase(any()) }

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when the return something then get empty paging list players since query and paging from api`(): Unit =
        runTest {
            //Given
            coEvery { repository.getPlayers(query = any()) } returns mockEmptyPagingPlayers
            //When
            val response = useCase(query = "James")
            //Then
            assert(response.count() > 0)
            assert(response.firstOrNull() == mockEmptyPagingDataPlayers)
            coVerify(exactly = 1) { useCase(any()) }

        }
}