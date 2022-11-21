package com.bcanon.nbacloneapp.players.data.datasources

import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity

val mockPlayersEntity = PlayersEntity(
    id = 1L,
    firstName = "Lebron",
    lastName = "James",
    heightFeet = 80,
    heightInches = 80,
    position = "F",
    weightPounds = 80
)

val mockListPlayersEntity = listOf(mockPlayersEntity)
