package com.bcanon.nbacloneapp.players.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity
import com.bcanon.nbacloneapp.players.data.datasources.LocalPlayersDataSource
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSource
import com.bcanon.nbacloneapp.players.data.paging.PlayersRemoteMediator
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getPlayers(query: String): Flow<PagingData<PlayersEntity>>
}

class PlayersRepositoryImpl(
    private val localDataSource: LocalPlayersDataSource,
    private val remoteDataSource: RemotePlayersDataSource
) : PlayersRepository {
    override fun getPlayers(query: String): Flow<PagingData<PlayersEntity>> {

        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { localDataSource.getPlayers(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = PlayersRemoteMediator(
                query,
                localDataSource, remoteDataSource

            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}