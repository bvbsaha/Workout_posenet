package com.bvbsaha.fitnesskursach.viewer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.TextView
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.Exercise
import com.bvbsaha.fitnesskursach.menu.MainActivity
import kotlinx.android.synthetic.main.activity_time_viewer.*



class TimeViewerActivity : AppCompatActivity() {

    lateinit var exercise: Exercise
    var close: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_viewer)
        exercise = StartActivity.workoutExercise[StartActivity.position]
        StartActivity.series++

        var currentSeries: TextView = findViewById(R.id.currentSeries)
        currentSeries.text = "Текущий подход: ${StartActivity.series}"
        if (StartActivity.series == exercise.series) {
            StartActivity.position++
            StartActivity.series = 0
        }
        MainActivity.workoutViewModel.updateDone(StartActivity.position,true)
        var title: TextView = findViewById(R.id.title_time_viewer)
        var description: TextView = findViewById(R.id.description_time_viewer)
        var instruction: TextView = findViewById(R.id.instruction_time_viewer)
        var series: TextView = findViewById(R.id.series_time_viewer)
        title.text = exercise.title
        description.text = exercise.description
        instruction.text = exercise.instruction
        series.text = exercise.series.toString()
        var time: TextView = findViewById(R.id.time_viewer)
        var timeSeconds: Int = exercise.time
        if (exercise.timeFormat != "секунды") {
            timeSeconds *= 60
        }
        progressBar.max = timeSeconds
        progressBar.progress = timeSeconds
        object : CountDownTimer((timeSeconds * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if (exercise.timeFormat == "секунды") {
                    time.text = "${millisUntilFinished / 1000} секунд"
                } else {
                    time.text = "${(millisUntilFinished / 60000).toInt()} мининут ${millisUntilFinished / 1000 % 60} sec"
                }
                if (close) {
                    cancel()
                }
                progressBar.progress = progressBar.progress - 1
            }

            override fun onFinish() {
                var intent: Intent = Intent(applicationContext, PauseActivity::class.java)
                intent.putExtra(TimeViewerActivity.pause, exercise.pause)
                intent.putExtra(pauseFormat, exercise.pauseFormat)
                startActivity(intent)
                var mediaPlayer: MediaPlayer? = MediaPlayer.create(applicationContext, R.raw.bell)
                mediaPlayer?.start()
                finish()
            }
        }.start()
        supportActionBar?.hide()
    }



    fun pause(view: View) {
        var intent: Intent = Intent(this, PauseActivity::class.java)
        intent.putExtra(pause, exercise.pause)
        intent.putExtra(pauseFormat, exercise.pauseFormat)
        startActivity(intent)
        close = true
        finish()
    }



    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Отмена тренировки")
            .setMessage("Вы уверены?")
            .setPositiveButton("OK") { dialog, which ->
                super.onBackPressed()
                StartActivity.series = 0
                close = true
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    companion object {
        const val pause = "com.bvbaha.fitnesskursach.pause"
        const val pauseFormat = "com.bvbsaha.fitnesskursach.pauseFormat"
    }
}
