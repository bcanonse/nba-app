package com.bcanon.nbacloneapp.players.data.network.dto

import com.squareup.moshi.Json

data class Team(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "full_name") val full_name: String,
    @field:Json(name = "city") val city: String,
    @field:Json(name = "abbreviation") val abbreviation: String,
    @field:Json(name = "conference") val conference: String,
    @field:Json(name = "division") val division: String,
    @field:Json(name = "name") val name: String
)