package com.bcanon.nbacloneapp.teams.data.repositories

import com.bcanon.nbacloneapp.teams.data.database.mappers.toDatabase
import com.bcanon.nbacloneapp.teams.data.datasources.LocalTeamsDataSource
import com.bcanon.nbacloneapp.teams.data.datasources.RemoteTeamsDataSource
import com.bcanon.nbacloneapp.teams.domain.mapper.toDomain
import com.bcanon.nbacloneapp.teams.domain.model.Teams

interface TeamsRepository {
    suspend fun getTeams(): Result<List<Teams>>
}

class TeamsRepositoryImpl(
    private val remoteTeamsDataSource: RemoteTeamsDataSource,
    private val localTeamsDataSource: LocalTeamsDataSource
) : TeamsRepository {
    override suspend fun getTeams(): Result<List<Teams>> {
        return try {
            val remoteData = remoteTeamsDataSource.getTeams()
            if (remoteData.isNotEmpty()) {
                localTeamsDataSource.deleteAllTeams()
                localTeamsDataSource.insertTeams(remoteData.map { it.toDatabase() })
                return Result.success(remoteData.map { it.toDomain() })
            } else {
                Result.success(localTeamsDataSource.getTeams().map { it.toDomain() })
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

