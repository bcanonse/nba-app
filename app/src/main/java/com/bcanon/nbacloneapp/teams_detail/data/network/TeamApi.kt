package com.bcanon.nbacloneapp.teams_detail.data.network

import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamApi {
    @GET("teams/{id}")
    suspend fun getTeamById(
        @Path("id") id: Int
    ): Response<TeamsDto>
}