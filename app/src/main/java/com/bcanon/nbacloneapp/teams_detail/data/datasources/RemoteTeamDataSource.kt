package com.bcanon.nbacloneapp.teams_detail.data.datasources

import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import com.bcanon.nbacloneapp.teams_detail.data.network.TeamApi
import kotlin.coroutines.cancellation.CancellationException

interface RemoteTeamDataSource {
    suspend fun getTeamById(id: Int) : TeamsDto?
}

class RemoteTeamDataSourceImpl(
    private val api: TeamApi
) : RemoteTeamDataSource {
    override suspend fun getTeamById(id: Int): TeamsDto? {
        try {
            val response = api.getTeamById(id)
            if (response.isSuccessful && response.code() == 200) {
                return response.body()
            }
            throw CancellationException()
        } catch (exception: Exception) {
            throw exception
        }
    }

}