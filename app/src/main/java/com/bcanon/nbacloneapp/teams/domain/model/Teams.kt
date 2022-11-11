package com.bcanon.nbacloneapp.teams.domain.model

data class Teams(
    val id: Int,
    val abbreviation: String,
    val conference: String,
    val name: String,
    val fullName: String,
)
