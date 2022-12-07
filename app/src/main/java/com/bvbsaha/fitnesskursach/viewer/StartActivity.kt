package com.bvbsaha.fitnesskursach.viewer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AlertDialog
import android.widget.ProgressBar
import android.widget.TextView
import com.bvbsaha.fitnesskursach.menu.MainActivity
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.Exercise
import com.bvbsaha.fitnesskursach.workout.WorkoutViewActivity


//класс для начала тренировки
class StartActivity : AppCompatActivity() {

    var close: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        workoutExercise.clear()
        val id = intent.getIntExtra(WorkoutViewActivity.WORKOUTID, 0)
        val workout = MainActivity.workoutViewModel.findWorkoutById(id)

        for (i in 0..((MainActivity.workoutViewModel.allExercise.value?.size?.minus(1)) ?: 0)) {
            if (MainActivity.workoutViewModel.allExercise.value?.get(i)?.workoutId == workout.id) {
                workoutExercise.add(MainActivity.workoutViewModel.allExercise.value?.get(i)!!)
            }
        }
        position = 0
        var intent: Intent
        if (!workoutExercise[0].timeCheck) {
            intent = Intent(applicationContext, RepeatViewerActivity::class.java)
            startActivity(intent)
        } else if (workoutExercise[0].timeCheck) {
            intent = Intent(applicationContext, TimeViewerActivity::class.java)
            startActivity(intent)
        }
        finish()
    }


    companion object {
        var workoutExercise: ArrayList<Exercise> = ArrayList()
        var position: Int = 0
        var series: Int = 0
    }
}