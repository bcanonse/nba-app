package com.bcanon.nbacloneapp.players.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersRemoteKeys

@Dao
interface PlayersRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PlayersRemoteKeys>)

    @Query("SELECT * FROM players_remote_keys WHERE playerId = :playerId")
    suspend fun remoteKeysPlayerId(playerId: Long): PlayersRemoteKeys?

    @Query("DELETE FROM players_remote_keys")
    suspend fun clearRemoteKeys()
}