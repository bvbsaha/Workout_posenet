package com.bvbsaha.fitnesskursach.database

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread

/** Workout Reposority is class contain
 * @property wordDao element for get all workout
 * @property allWorkout has all workouе
 */

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    var allWorkout: LiveData<List<Workout>> = workoutDao.getAllWorkout()

   //Запись нового элемента в бд

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
        allWorkout = workoutDao.getAllWorkout()
    }

    //Удаление элмента
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        workoutDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteById(id: Int) {
        workoutDao.removeById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateWorkoutTitle(id: Int, title : String) {
        workoutDao.updateWorkoutTitle(title,id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateWorkoutDescription(id: Int, description : String) {
        workoutDao.updateWorkoutDescription(description,id)
    }

     fun searchDatabase(searchQuery:String):LiveData<List<Workout>>{
        return workoutDao.searchDatabase(searchQuery)
    }
}