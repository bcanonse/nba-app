package com.bcanon.nbacloneapp.teams.data.network.dto

import com.squareup.moshi.Json

data class TeamsDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "abbreviation") val abbreviation: String,
    @field:Json(name = "city") val city: String,
    @field:Json(name = "conference") val conference: String,
    @field:Json(name = "division") val division: String,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "name") val name: String,
)
