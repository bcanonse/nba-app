package com.bcanon.nbacloneapp.teams.data.datasources

import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalTeamsDataSourceImplTest {
    @RelaxedMockK
    private lateinit var dao: TeamsDao

    private lateinit var localDataSource: LocalTeamsDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDataSource = LocalTeamsDataSourceImpl(
            dao = dao
        )
    }

    @Test
    fun `When is insert list teams in database using local remote data source`() = runBlocking {

        // Just run method using returns Unit
//        coEvery { dao.insertTeams(any()) } returns Unit//
//        coEvery { dao.insertTeams(any()) } just runs
        // GIVEN
        // Only just run and return Unit
        coJustRun { dao.insertTeams(any()) }

        // WHEN
        localDataSource.insertTeams(mockListTeamsEntity)
        // THEN - Same task is returned.
        coVerify(exactly = 1) { dao.insertTeams(any()) }
    }

    @Test
    fun `When is delete list teams in database using local remote data source`() = runBlocking {
        // GIVEN
        // Only just run and return Unit
        coJustRun { dao.deleteAllTeams() }

        // WHEN
        localDataSource.deleteAllTeams()
        // THEN - Same task is returned.
        coVerify(exactly = 1) { dao.deleteAllTeams() }
    }

    @Test
    fun `When is get list teams in database using local remote data source`() = runBlocking {
        // GIVEN
        coEvery { dao.getTeams() } returns mockListTeamsEntity

        // WHEN
        val result = localDataSource.getTeams()
        // THEN - Same task is returned.
        coVerify(exactly = 1) { dao.getTeams() }
        assert(result == mockListTeamsEntity)
        assert(result.isNotEmpty())
    }
}
