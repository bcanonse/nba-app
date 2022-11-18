package com.bcanon.nbacloneapp.players.data.datasources

import androidx.paging.PagingSource
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersDao
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersRemoteKeysDao
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersRemoteKeys

interface LocalPlayersDataSource {
    fun getPlayers(query: String): PagingSource<Int, PlayersEntity>

    suspend fun insertPlayers(players: List<PlayersEntity>)

    suspend fun deleteAllPlayers()

    suspend fun insertRemoteKeys(remoteKeys: List<PlayersRemoteKeys>)

    suspend fun clearRemoteKeys()

    suspend fun remoteKeysPlayerId(playerId: Long): PlayersRemoteKeys?
}

class LocalPlayersDataSourceImpl(
    private val playersDao: PlayersDao,
    private val remoteKeysDao: PlayersRemoteKeysDao
) : LocalPlayersDataSource {
    override fun getPlayers(query: String): PagingSource<Int, PlayersEntity> =
        playersDao.getPlayers(query = query)

    override suspend fun insertPlayers(players: List<PlayersEntity>) =
        playersDao.insertPlayers(players = players)

    override suspend fun deleteAllPlayers() = playersDao.deleteAllPlayers()

    override suspend fun insertRemoteKeys(remoteKeys: List<PlayersRemoteKeys>) =
        remoteKeysDao.insertAll(remoteKey = remoteKeys)

    override suspend fun clearRemoteKeys() = remoteKeysDao.clearRemoteKeys()

    override suspend fun remoteKeysPlayerId(playerId: Long): PlayersRemoteKeys? =
        remoteKeysDao.remoteKeysPlayerId(playerId = playerId)

}
