package com.mateusz.workoutcustomer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * It is data class Exercise
 * @author Mateusz Karłowski
 */

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey
    @ColumnInfo(name = "_id") var id: Int,
    @ColumnInfo(name = "workoutId") var workoutId: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "instruction") var instruction: String,
    @ColumnInfo(name = "series") var series: Int,
    @ColumnInfo(name = "timeCheck") var timeCheck: Boolean,
    @ColumnInfo(name = "time") var time: Int,
    @ColumnInfo(name = "timeFormat") var timeFormat: String,
    @ColumnInfo(name = "repeat") var repeat: Int,
    @ColumnInfo(name = "pause") var pause: Int,
    @ColumnInfo(name = "pauseFormat") var pauseFormat: String
)