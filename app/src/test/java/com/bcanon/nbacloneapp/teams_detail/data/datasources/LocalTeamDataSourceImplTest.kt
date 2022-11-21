package com.bcanon.nbacloneapp.teams_detail.data.datasources

import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams.data.datasources.mockTeamsEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalTeamDataSourceImplTest {
    @RelaxedMockK
    private lateinit var dao: TeamsDao

    private lateinit var localDataSource: LocalTeamDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDataSource = LocalTeamDataSourceImpl(
            dao = dao
        )
    }

    @Test
    fun `When is get one team from by id in database using local remote data source`() =
        runBlocking {
            // GIVEN
            coEvery { dao.getTeamsById(any()) } returns mockTeamsEntity

            // WHEN
            val result = localDataSource.getTeamById(id = 1)
            // THEN - Same task is returned.
            coVerify(exactly = 1) { localDataSource.getTeamById(any()) }
            assert(result == mockTeamsEntity)
            assert(result?.javaClass == mockTeamsEntity.javaClass)
            assert(result != null)
        }

    @Test
    fun `When is get one team from by id, return null in database using local remote data source`() =
        runBlocking {
            // GIVEN
            coEvery { dao.getTeamsById(any()) } returns null

            // WHEN
            val result = localDataSource.getTeamById(id = 1)
            // THEN - Same task is returned.
            coVerify(exactly = 1) { localDataSource.getTeamById(any()) }
            assertNull(result)
        }

}