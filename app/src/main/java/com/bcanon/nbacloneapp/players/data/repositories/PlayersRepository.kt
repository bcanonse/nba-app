package com.bcanon.nbacloneapp.players.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bcanon.nbacloneapp.players.data.datasources.LocalPlayersDataSource
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSource
import com.bcanon.nbacloneapp.players.data.paging.PlayersPagingSource
import com.bcanon.nbacloneapp.players.domain.model.Players
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getPlayers(query: String): Flow<PagingData<Players>>
}

class PlayersRepositoryImpl(
    private val localDataSource: LocalPlayersDataSource,
    private val remoteDataSource: RemotePlayersDataSource
) : PlayersRepository {
    override fun getPlayers(query: String): Flow<PagingData<Players>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            maxSize = MAX_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PlayersPagingSource(query, remoteDataSource) }
    ).flow

    private companion object {
        const val NETWORK_PAGE_SIZE = 30
        const val MAX_PAGE_SIZE = 100
    }
}