package com.bcanon.nbacloneapp.home.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersDao
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersRemoteKeysDao
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersRemoteKeys
import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity

@Database(
    entities = [TeamsEntity::class, PlayersEntity::class, PlayersRemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class NbaDatabase : RoomDatabase() {

    abstract fun getTeamsDao(): TeamsDao
    abstract fun getPlayersDao(): PlayersDao
    abstract fun getRemoteKeysPlayersDao(): PlayersRemoteKeysDao
}