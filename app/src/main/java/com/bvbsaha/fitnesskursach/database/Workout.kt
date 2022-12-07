package com.bvbsaha.fitnesskursach.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey
    @ColumnInfo(name = "_id") var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String
)