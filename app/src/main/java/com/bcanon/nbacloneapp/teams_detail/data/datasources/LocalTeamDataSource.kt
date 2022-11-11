package com.bcanon.nbacloneapp.teams_detail.data.datasources

import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity

interface LocalTeamDataSource {
    suspend fun getTeamById(id: Int): TeamsEntity?
}

class LocalTeamDataSourceImpl(
    private val dao: TeamsDao
) : LocalTeamDataSource {
    override suspend fun getTeamById(id: Int): TeamsEntity? = dao.getTeamsById(id = id)
}