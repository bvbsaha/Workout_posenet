package com.bvbsaha.fitnesskursach.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WorkoutDao {

   //Запись новой тренировки

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workout: Workout)

    //Удаление тренировки

    @Query("DELETE FROM workout_table")
    fun deleteAll()

    //Получение тренировки

    @Query("SELECT * from workout_table ORDER BY _id, title, description ASC")
    fun getAllWorkout(): LiveData<List<Workout>>

    /**
     * Function **removeById** remove workout with the same id
     */

    @Query("DELETE FROM workout_table WHERE _id LIKE :ID")
    fun removeById(ID: Int)

    @Query("UPDATE workout_table SET title=:title WHERE _id LIKE :ID")
    fun updateWorkoutTitle(title: String, ID: Int)

    @Query("UPDATE workout_table SET description=:description WHERE _id LIKE :ID")
    fun updateWorkoutDescription(description: String, ID: Int)

    @Query("SELECT * FROM workout_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery:String):LiveData<List<Workout>>
}