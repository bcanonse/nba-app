package com.bcanon.nbacloneapp.teams.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,

    @ColumnInfo(name = "abbreviation") val abbreviation: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "conference") val conference: String,
    @ColumnInfo(name = "division") val division: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "name") val name: String,
)


