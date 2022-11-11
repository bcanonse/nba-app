package com.bcanon.nbacloneapp.teams_detail.data.repositories

import com.bcanon.nbacloneapp.teams.domain.mapper.toDomain
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams_detail.data.datasources.LocalTeamDataSource
import com.bcanon.nbacloneapp.teams_detail.data.datasources.RemoteTeamDataSource
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

interface TeamRepository {
    suspend fun getTeamById(id: Int): Result<Teams?>
}

class TeamRepositoryImpl(
    private val remoteDataSource: RemoteTeamDataSource,
    private val localDataSource: LocalTeamDataSource
) : TeamRepository {
    override suspend fun getTeamById(id: Int): Result<Teams?> {
        return try {
            val remoteData = remoteDataSource.getTeamById(id = id)
                ?: return Result.success(localDataSource.getTeamById(id = id)?.toDomain())
            Result.success(remoteData.toDomain())
        } catch (exception: Exception) {
            when (exception) {
                is SocketTimeoutException,
                is CancellationException -> {
                    return Result.success(localDataSource.getTeamById(id = id)?.toDomain())
                }
                else -> {
                    return Result.failure(exception)
                }
            }
        }
    }

}