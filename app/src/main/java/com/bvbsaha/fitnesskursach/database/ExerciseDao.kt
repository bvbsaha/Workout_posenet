package com.bvbsaha.fitnesskursach.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ExerciseDao {

   //Запись нового упражнения

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exercise: Exercise)

    //Получение упражнения
    @Query("SELECT * from exercise_table ORDER BY _id, title, description, instruction, series, timeCheck, time, timeFormat, repeat, pause, pauseFormat ASC")
    fun getAllExercise(): LiveData<List<Exercise>>

    //Удаление упражнения по id

    @Query("DELETE FROM exercise_table WHERE _id LIKE :index")
    fun deleteByExerciseId(index: Int)

    //Удаление упражнения по id тренировки

    @Query("DELETE FROM exercise_table WHERE workoutId LIKE :index")
    fun deleteByWorkoutId(index: Int)

    //Удаление упражнения

    @Query("DELETE FROM exercise_table")
    fun deleteAll()

    @Query("UPDATE exercise_table SET title=:title WHERE _id LIKE :ID")
    fun updateExerciseTitle(title: String, ID: Int)

    @Query("UPDATE exercise_table SET _id=:toID WHERE _id LIKE :fromID")
    fun changeExerciseID(toID: Int, fromID: Int)

    @Query("UPDATE exercise_table SET description=:description WHERE _id LIKE :ID")
    fun updateExerciseDescription(description: String, ID: Int)

    @Query("UPDATE exercise_table SET instruction=:instruction WHERE _id LIKE :ID")
    fun updateExerciseInstruction(instruction: String, ID: Int)

    @Query("UPDATE exercise_table SET series=:series WHERE _id LIKE :ID")
    fun updateExerciseSeries(series: Int, ID: Int)

    @Query("UPDATE exercise_table SET time=:time WHERE _id LIKE :ID")
    fun updateExerciseTime(time: Int, ID: Int)

    @Query("UPDATE exercise_table SET timeFormat=:timeFormat WHERE _id LIKE :ID")
    fun updateExerciseTimeFormat(timeFormat: String, ID: Int)

    @Query("UPDATE exercise_table SET repeat=:repeat WHERE _id LIKE :ID")
    fun updateExerciseRepeat(repeat: Int, ID: Int)

    @Query("UPDATE exercise_table SET pause=:pause WHERE _id LIKE :ID")
    fun updateExercisePause(pause: Int, ID: Int)

    @Query("UPDATE exercise_table SET done=:done WHERE _id LIKE :ID")
    fun updateDone(done:Boolean,ID: Int)

    @Query("UPDATE exercise_table SET pauseFormat=:pauseFormat WHERE _id LIKE :ID")
    fun updateExercisePauseFormat(pauseFormat: String, ID: Int)
}