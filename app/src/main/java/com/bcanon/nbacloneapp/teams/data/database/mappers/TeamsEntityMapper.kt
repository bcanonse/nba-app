package com.bcanon.nbacloneapp.teams.data.database.mappers

import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto

fun TeamsDto.toDatabase() = TeamsEntity(
    id, abbreviation, city, conference, division, fullName, name
)