package com.bcanon.nbacloneapp.teams.data.repositories

import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity
import com.bcanon.nbacloneapp.teams.data.datasources.LocalTeamsDataSource
import com.bcanon.nbacloneapp.teams.data.datasources.RemoteTeamsDataSource
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import com.bcanon.nbacloneapp.teams.domain.mapper.toDomain
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class TeamsRepositoryImplTest {

    private companion object {
        private val mockListDto = listOf(
            TeamsDto(
                id = 1,
                name = "Atlanta",
                conference = "East",
                city = "Atlanta",
                division = "East",
                abbreviation = "Hawks",
                fullName = "Atlanta Hawks"
            ),
            TeamsDto(
                id = 2,
                name = "San Antonio",
                conference = "West",
                city = "Texas",
                division = "West",
                abbreviation = "Spurs",
                fullName = "San Antonio Spurs"
            )
        )

        private val mockListEntity = listOf(
            TeamsEntity(
                id = 1,
                name = "Atlanta",
                conference = "East",
                city = "Atlanta",
                division = "East",
                abbreviation = "Hawks",
                fullName = "Atlanta Hawks"
            ),
            TeamsEntity(
                id = 2,
                name = "San Antonio",
                conference = "West",
                city = "Texas",
                division = "West",
                abbreviation = "Spurs",
                fullName = "San Antonio Spurs"
            )
        )
    }

    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteTeamsDataSource

    @RelaxedMockK
    private lateinit var localDataSource: LocalTeamsDataSource

    private lateinit var repository: TeamsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = TeamsRepositoryImpl(
            remoteTeamsDataSource = remoteDataSource,
            localTeamsDataSource = localDataSource
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `When get list teams from remote data source and correct maps entity into business object`() =
        runBlocking {
            //Given
            coEvery { remoteDataSource.getTeams() } returns mockListDto

            //When
            val response = repository.getTeams()
            val teams = response.getOrNull() ?: any()

            //Then
            coVerify { remoteDataSource.getTeams() }
            assert(response.isSuccess)
            assert(teams.isNotEmpty())
            assert(teams == mockListDto.map { it.toDomain() })
            assert(teams[0].id == mockListDto.map { it.toDomain() }[0].id)
        }

    @Test
    fun `When get list empty teams from remote data source since repository`() =
        runBlocking {
            //Given
            coEvery { remoteDataSource.getTeams() } returns emptyList()

            //When
            val response = repository.getTeams()
            val teams = response.getOrNull() ?: any()

            //Then
            coVerify { remoteDataSource.getTeams() }
            assert(response.isSuccess)
            assert(teams.isEmpty())
            assert(teams == emptyList<Teams>())
        }

    @Test
    fun `When get list teams from local data source and correct maps entity into business object`() =
        runBlocking {
            //Given
            coEvery { localDataSource.getTeams() } returns mockListEntity

            //When
            val response = repository.getTeams()
            val teams = response.getOrNull() ?: any()

            //Then
            coVerify(exactly = 1) { localDataSource.getTeams() }
            assert(response.isSuccess)
            assert(teams.isNotEmpty())
            assert(teams == mockListDto.map { it.toDomain() })
            assert(teams[0].id == mockListDto.map { it.toDomain() }[0].id)
        }

    @Test
    fun `When get list empty teams from local data source since repository`() =
        runBlocking {
            //Given
            coEvery { localDataSource.getTeams() } returns emptyList()

            //When
            val response = repository.getTeams()
            val teams = response.getOrNull() ?: any()

            //Then
            coVerify(exactly = 1) { localDataSource.getTeams() }
            assert(response.isSuccess)
            assert(teams.isEmpty())
            assert(teams == emptyList<Teams>())
        }

    @Test
    fun `When get list teams from remote data source and delete items database and insert collection in database, after correct maps entity into business object`() =
        runBlocking {
            //Given
            coEvery { remoteDataSource.getTeams() } returns mockListDto

            //When
            val response = repository.getTeams()
            val teams = response.getOrNull() ?: any()

            //Then
            coVerify(exactly = 1) { remoteDataSource.getTeams() }
            coVerify(exactly = 1) { localDataSource.deleteAllTeams() }
            coVerify(exactly = 1) { localDataSource.insertTeams(any()) }
            coVerify(exactly = 0) { localDataSource.getTeams() }

            assert(response.isSuccess)
            assert(teams.isNotEmpty())
            assert(teams == mockListDto.map { it.toDomain() })
        }
}