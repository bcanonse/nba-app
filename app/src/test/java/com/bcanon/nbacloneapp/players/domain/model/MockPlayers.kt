package com.bcanon.nbacloneapp.players.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf

val players = Players(
    id = 1L,
    fullName = "Lebron James",
    position = "F"
)

val playersList = listOf(players)

val mockPagingDataPlayers = PagingData.from(playersList)
val mockEmptyPagingDataPlayers = PagingData.empty<Players>()

val mockPagingPlayers = flowOf(mockPagingDataPlayers)
val mockEmptyPagingPlayers = flowOf(mockEmptyPagingDataPlayers)