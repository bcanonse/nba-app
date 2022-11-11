package com.bcanon.nbacloneapp.teams.domain.model

import com.squareup.moshi.Json

data class Teams(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "abbreviation") val abbreviation: String,
    @field:Json(name = "conference") val conference: String,
    @field:Json(name = "name") val name: String,
)
