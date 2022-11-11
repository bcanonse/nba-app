package com.bcanon.nbacloneapp.teams.data.datasources

import com.bcanon.nbacloneapp.teams.data.network.TeamsApi
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import kotlin.coroutines.cancellation.CancellationException

interface RemoteTeamsDataSource {
    suspend fun getTeams(): List<TeamsDto>
}

class RemoteTeamsDataSourceImpl(
    private val teamsApi: TeamsApi
) : RemoteTeamsDataSource {
    override suspend fun getTeams(): List<TeamsDto> {
        try {
            val response = teamsApi.getTeams()
            if (response.isSuccessful && response.code() == 200) {
                return response.body()?.data ?: emptyList()
            }
            throw CancellationException()
        } catch (e: Exception) {
            throw e
        }
    }

}