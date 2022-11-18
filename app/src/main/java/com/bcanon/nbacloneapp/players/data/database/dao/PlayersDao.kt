package com.bcanon.nbacloneapp.players.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity

@Dao
interface PlayersDao {
    @Query("SELECT * FROM players WHERE first_name LIKE :query OR last_name LIKE :query ORDER BY id, first_name, last_name ASC")
    fun getPlayers(query: String): PagingSource<Int, PlayersEntity>

    @Query("SELECT * FROM players WHERE id = :id")
    suspend fun getPlayersById(id: Int): PlayersEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<PlayersEntity>)

    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()
}