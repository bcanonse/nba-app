package com.bcanon.nbacloneapp.players.data.network.dto

import com.squareup.moshi.Json

data class PlayersResponse(
    @field:Json(name = "data") val data: List<PlayersDto>
)