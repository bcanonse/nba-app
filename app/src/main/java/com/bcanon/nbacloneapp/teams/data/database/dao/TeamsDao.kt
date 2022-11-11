package com.bcanon.nbacloneapp.teams.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity

@Dao
interface TeamsDao {
    @Query("SELECT * FROM teams ORDER BY id, name ASC")
    suspend fun getTeams(): List<TeamsEntity>

    @Query("SELECT * FROM teams WHERE id = :id")
    suspend fun getTeamsById(id: Int): TeamsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<TeamsEntity>)

    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()
}