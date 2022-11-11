package com.bcanon.nbacloneapp.teams_detail.data.repositories

import com.bcanon.nbacloneapp.teams.domain.mapper.toDomain
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams_detail.data.datasources.LocalTeamDataSource
import com.bcanon.nbacloneapp.teams_detail.data.datasources.RemoteTeamDataSource

interface TeamRepository {
    suspend fun getTeamById(id: Int): Result<Teams?>
}

class TeamRepositoryImpl(
    private val remoteDataSource: RemoteTeamDataSource,
    private val localDataSource: LocalTeamDataSource
) : TeamRepository {
    override suspend fun getTeamById(id: Int): Result<Teams?> = try {
        val remoteData = remoteDataSource.getTeamById(id = id)
        if (remoteData == null) localDataSource.getTeamById(id = id)
        Result.success(remoteData?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

}