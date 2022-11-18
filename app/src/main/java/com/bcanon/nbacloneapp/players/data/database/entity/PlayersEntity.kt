package com.bcanon.nbacloneapp.players.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayersEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,

    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "height_feet") val heightFeet: Int?,
    @ColumnInfo(name = "height_inches") val heightInches: Int?,
    @ColumnInfo(name = "position") val position: String,
    @ColumnInfo(name = "weight_pounds") val weightPounds: Int?,
)
