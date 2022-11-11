package com.bcanon.nbacloneapp.teams_detail.domain.usecase

import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams_detail.data.repositories.TeamRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTeamUseCaseTest {

    private companion object {
        private val mockTeam = Teams(
            id = 1,
            name = "Atlanta",
            abbreviation = "Hawks",
            conference = "East",
            fullName = "Atlanta Hawks"
        )
        private val mockResult = Result.success(mockTeam)
        private val mockResultNull = Result.success(null)
    }

    @RelaxedMockK
    private lateinit var repository: TeamRepository

    private lateinit var useCase: GetTeamUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetTeamUseCase(repository)
    }

    @Test
    fun `when the return something then get object team since id from api or database`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getTeamById(any()) } returns mockResult
            //When
            val response = useCase(
                id = 1
            )
            //Then
            assert(response.isSuccess)
            assert(response.getOrNull() == mockTeam)

        }

    @Test
    fun `when the repository call only return something then get object team from id from api or db`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getTeamById(any()) } returns mockResult
            //When
            val response = useCase(
                id = 1
            )
            //Then
            assert(response.isSuccess)
            coVerify(exactly = 1) { useCase(any()) }

        }

    @Test
    fun `when the repository doesn't return object team from api or database and return exception`(): Unit =
        runBlocking {
            //Given
            coEvery { repository.getTeamById(any()) } throws (Throwable())

            //When
            var exceptionThrown = false
            try {
                useCase(
                    id = 1
                )
            } catch (e: Throwable) {
                exceptionThrown = true
            }
            assert(exceptionThrown)
            //Then
            coVerify(exactly = 1) {
                useCase(
                    id = 1
                )
            }

        }

    @Test
    fun `When database or api is empty then return null`(): Unit = runBlocking {
        //Given
        coEvery { repository.getTeamById(any()) } returns mockResultNull

        //When
        val response = useCase(
            id = 1
        )
        //Then
        assert(response.getOrNull() == null)
    }
}