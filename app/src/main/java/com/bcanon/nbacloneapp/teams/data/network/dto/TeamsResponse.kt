package com.bcanon.nbacloneapp.teams.data.network.dto

import com.squareup.moshi.Json

data class TeamsResponse(
    @field:Json(name = "data") val data: List<TeamsDto>,
)