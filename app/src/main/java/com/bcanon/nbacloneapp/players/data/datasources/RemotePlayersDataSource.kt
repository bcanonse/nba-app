package com.bcanon.nbacloneapp.players.data.datasources

import com.bcanon.nbacloneapp.players.data.network.PlayersApi
import com.bcanon.nbacloneapp.players.data.network.dto.PlayersDto
import kotlin.coroutines.cancellation.CancellationException

interface RemotePlayersDataSource {
    suspend fun getPlayers(
        query: String,
        page: Int,
        perPage: Int
    ): List<PlayersDto>
}

class RemotePlayersDataSourceImpl(
    private val playersApi: PlayersApi
) : RemotePlayersDataSource {
    override suspend fun getPlayers(
        query: String,
        page: Int,
        perPage: Int
    ): List<PlayersDto> {
        try {
            val response = playersApi.getPlayers(
                query = query,
                perPage = perPage,
                page = page
            )
            if (response.isSuccessful && response.code() == 200) {
                return response.body()?.data ?: emptyList()
            }
            throw CancellationException()
        } catch (e: Exception) {
            throw e
        }
    }
}