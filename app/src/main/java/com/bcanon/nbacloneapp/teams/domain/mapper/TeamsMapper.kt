package com.bcanon.nbacloneapp.teams.domain.mapper

import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import com.bcanon.nbacloneapp.teams.domain.model.Teams

fun TeamsDto.toDomain() = Teams(
    id, abbreviation, conference, name, fullName
)

fun TeamsEntity.toDomain() = Teams(
    id, abbreviation, conference, name, fullName
)