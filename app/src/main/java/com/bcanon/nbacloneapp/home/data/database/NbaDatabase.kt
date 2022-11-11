package com.bcanon.nbacloneapp.home.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity

@Database(entities = [TeamsEntity::class], version = 1, exportSchema = false)
abstract class NbaDatabase : RoomDatabase() {

    abstract fun getTeamsDao(): TeamsDao
}