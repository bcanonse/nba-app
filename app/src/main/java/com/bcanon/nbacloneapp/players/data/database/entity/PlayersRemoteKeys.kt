package com.bcanon.nbacloneapp.players.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_remote_keys")
data class PlayersRemoteKeys(
    @PrimaryKey val playerId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
