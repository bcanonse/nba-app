package com.bcanon.nbacloneapp.players.data.datasources

import com.bcanon.nbacloneapp.players.data.database.dao.PlayersDao
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersRemoteKeysDao
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalPlayersDataSourceImplTest {
    @RelaxedMockK
    private lateinit var daoPlayers: PlayersDao

    @RelaxedMockK
    private lateinit var daoRemoteKeys: PlayersRemoteKeysDao

    private lateinit var localDataSource: LocalPlayersDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDataSource = LocalPlayersDataSourceImpl(
            playersDao = daoPlayers,
            remoteKeysDao = daoRemoteKeys
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `When is insert list players in database using local remote data source`() = runBlocking {
        // Given
        coJustRun { daoPlayers.insertPlayers(any()) }

        // WHEN
        localDataSource.insertPlayers(mockListPlayersEntity)
        // THEN - Same task is returned.
        coVerify(exactly = 1) { localDataSource.insertPlayers(any()) }
    }

    @Test
    fun `When is delete list players in database using local remote data source`() = runBlocking {
        // GIVEN
        // Only just run and return Unit
        coJustRun { daoPlayers.deleteAllPlayers() }

        // WHEN
        localDataSource.deleteAllPlayers()
        // THEN - Same task is returned.
        coVerify(exactly = 1) { daoPlayers.deleteAllPlayers() }
    }
}