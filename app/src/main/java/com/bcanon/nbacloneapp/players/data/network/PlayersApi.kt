package com.bcanon.nbacloneapp.players.data.network

import com.bcanon.nbacloneapp.players.data.network.dto.PlayersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayersApi {
    @GET("players/")
    suspend fun getPlayers(
        @Query("search") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): Response<PlayersResponse>
}