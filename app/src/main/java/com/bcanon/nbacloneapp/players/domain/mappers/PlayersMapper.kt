package com.bcanon.nbacloneapp.players.domain.mappers

import androidx.paging.PagingData
import androidx.paging.map
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity
import com.bcanon.nbacloneapp.players.data.network.dto.PlayersDto
import com.bcanon.nbacloneapp.players.domain.model.Players

fun PlayersEntity.toDomain() = Players(
    id = id,
    fullName = "$firstName $lastName",
    position = position
)

fun PlayersDto.toDomain() = Players(
    id = id,
    fullName = "$firstName $lastName",
    position = position
)

fun List<PlayersDto>.remoteToDomain() = this.map { it.toDomain() }

fun List<PlayersEntity>.dbToDomain() = this.map { it.toDomain() }

fun PagingData<PlayersEntity>.toDomain() = this.map { it.toDomain() }