package com.bcanon.nbacloneapp.teams.data.datasources

import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity

interface LocalTeamsDataSource {
    suspend fun getTeams(): List<TeamsEntity>

    suspend fun insertTeams(teams: List<TeamsEntity>)

    suspend fun deleteAllTeams()
}

class LocalTeamsDataSourceImpl(
    private val dao: TeamsDao
) : LocalTeamsDataSource {
    override suspend fun getTeams(): List<TeamsEntity> = dao.getTeams()

    override suspend fun insertTeams(teams: List<TeamsEntity>) = dao.insertTeams(teams)

    override suspend fun deleteAllTeams() = dao.deleteAllTeams()
}