package com.bcanon.nbacloneapp.teams.domain.usecase

import com.bcanon.nbacloneapp.teams.data.repositories.TeamsRepository
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTeamsUseCaseTest {

    private companion object {
        private val mockList = listOf(
            Teams(
                id = 1,
                name = "Atlanta",
                abbreviation = "Hawks",
                conference = "East",
            )
        )
        private val mockResultList = Result.success(mockList)
    }

    @RelaxedMockK
    private lateinit var repository: TeamsRepository

    private lateinit var useCase: GetTeamsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetTeamsUseCase(repository)
    }

    @Test
    fun `when the return something then get list teams from api or database`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getTeams() } returns mockResultList
            //When
            val response = useCase()
            //Then
            assert(response.isSuccess)
            assert(response.getOrNull() == mockList)

        }

    @Test
    fun `when the repository call only return something then get list teams from api or db`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getTeams() } returns mockResultList
            //When
            val response = useCase()
            //Then
            assert(response.isSuccess)
            coVerify(exactly = 1) { useCase() }

        }

    @Test
    fun `when the repository doesn't return list teams from api or database and return exception`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getTeams() } throws (Throwable())

            //When
            var exceptionThrown = false
            try {
                useCase()
            } catch (e: Throwable) {
                exceptionThrown = true
            }
            assert(exceptionThrown)
            //Then
            coVerify(exactly = 1) { useCase() }

        }
}