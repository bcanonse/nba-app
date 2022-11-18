package com.bcanon.nbacloneapp.players.data.database.mappers

import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity
import com.bcanon.nbacloneapp.players.data.network.dto.PlayersDto

fun PlayersDto.toDatabase() = PlayersEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    weightPounds = weighPounds,
    heightFeet = heightFeet,
    heightInches = heightInches,
    position = position
)

fun List<PlayersDto>.toDatabase() = this.map { it.toDatabase() }
