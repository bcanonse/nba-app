package com.bcanon.nbacloneapp.teams.data.network

import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TeamsApi {
    @GET("teams/")
    suspend fun getTeams(): Response<TeamsResponse>
}