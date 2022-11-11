package com.bcanon.nbacloneapp.teams_detail.data.repositories

import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import com.bcanon.nbacloneapp.teams.domain.mapper.toDomain
import com.bcanon.nbacloneapp.teams_detail.data.datasources.LocalTeamDataSource
import com.bcanon.nbacloneapp.teams_detail.data.datasources.RemoteTeamDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class TeamRepositoryImplTest {

    private companion object {
        private val mockDto = TeamsDto(
            id = 1,
            name = "Atlanta",
            conference = "East",
            city = "Atlanta",
            division = "East",
            abbreviation = "Hawks",
            fullName = "Atlanta Hawks"
        )

        private val mockEntity = TeamsEntity(
            id = 1,
            name = "Atlanta",
            conference = "East",
            city = "Atlanta",
            division = "East",
            abbreviation = "Hawks",
            fullName = "Atlanta Hawks"
        )
    }

    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteTeamDataSource

    @RelaxedMockK
    private lateinit var localDataSource: LocalTeamDataSource

    private lateinit var repository: TeamRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = TeamRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `When get team by id and return object team from remote data source and correct maps entity into business object`() =
        runBlocking {
            //Given
            coEvery { remoteDataSource.getTeamById(any()) } returns mockDto

            //When
            val response = repository.getTeamById(id = 1)
            val team = response.getOrNull()

            //Then
            coVerify(exactly = 1) { remoteDataSource.getTeamById(any()) }
            coVerify(exactly = 0) { localDataSource.getTeamById(any()) }
            assert(response.isSuccess)
            assertNotNull(team)
            assert(team == mockDto.toDomain())
            assert(team?.id == mockDto.toDomain().id)
        }

    @Test
    fun `When get team by id from remote data source and return null, so get the team from local data source since repository`() =
        runBlocking {
            //Given
            coEvery { remoteDataSource.getTeamById(any()) } returns null
            coEvery { localDataSource.getTeamById(any()) } returns mockEntity

            //When
            val response = repository.getTeamById(id = 1)
            val team = response.getOrNull()

            //Then
            coVerify(exactly = 1) { remoteDataSource.getTeamById(any()) }
            coVerify(exactly = 1) { localDataSource.getTeamById(any()) }
            assert(response.isSuccess)
            assertNotNull(team)
            assert(team == mockEntity.toDomain())
            assert(team?.id == mockEntity.toDomain().id)
        }

    @Test
    fun `When get team by id and return null team from local data source since repository`() =
        runBlocking {
            //Given
            coEvery { remoteDataSource.getTeamById(any()) } returns null
            coEvery { localDataSource.getTeamById(any()) } returns null

            //When
            val response = repository.getTeamById(id = 1)
            val team = response.getOrNull()

            //Then
            coVerify(exactly = 1) { remoteDataSource.getTeamById(any()) }
            coVerify(exactly = 1) { localDataSource.getTeamById(any()) }
            assert(response.isSuccess)
            assertNull(team)
        }

}