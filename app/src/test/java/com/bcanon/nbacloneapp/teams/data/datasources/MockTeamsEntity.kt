package com.bcanon.nbacloneapp.teams.data.datasources

import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity

val mockTeamsEntity = TeamsEntity(
    id = 1,
    abbreviation = "",
    city = "",
    conference = "",
    division = "",
    fullName = "",
    name = ""
)

val mockListTeamsEntity = listOf(mockTeamsEntity)