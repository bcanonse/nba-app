package com.bcanon.nbacloneapp.players.data.network.dto

import com.squareup.moshi.Json

data class PlayersDto(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "first_name") val firstName: String,
    @field:Json(name = "height_feet") val heightFeet: Int?,
    @field:Json(name = "height_inches") val heightInches: Int?,
    @field:Json(name = "last_name") val lastName: String,
    @field:Json(name = "position") val position: String,
    @field:Json(name = "team") val team: Team,
    @field:Json(name = "weight_pounds") val weighPounds: Int?
)