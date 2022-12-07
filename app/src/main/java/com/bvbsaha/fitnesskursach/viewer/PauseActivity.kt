package com.bvbsaha.fitnesskursach.viewer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.menu.MenuActivity

class PauseActivity : AppCompatActivity() {

    var close: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pause)
        var nextExericse: TextView= findViewById(R.id.nextExercise)
        try {
            nextExericse.text = "Следующее упражнение: ${StartActivity.workoutExercise[StartActivity.position].title}"
        }
        catch (e: Exception){
            nextExericse.text = ""
        }
        var time: TextView = findViewById(R.id.time_pause_view)
        var progresBar: ProgressBar = findViewById(R.id.progressBar2)
        var timeSeconds = intent.getIntExtra(TimeViewerActivity.pause, 0)
        var timeFormat = intent.getStringExtra(TimeViewerActivity.pauseFormat)
        if (timeFormat != "seconds") {
            timeSeconds *= 60
        }
        progresBar.max = timeSeconds
        progresBar.progress = timeSeconds

        object : CountDownTimer(timeSeconds * 1000.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if (timeFormat == "seconds") {
                    time.text = "${millisUntilFinished / 1000} сек"
                } else {
                    time.text = "${(millisUntilFinished / 60000).toInt()} мин ${millisUntilFinished / 1000 % 60} сек"
                }
                if (close) {
                    cancel()
                }
                progresBar.progress = progresBar.progress - 1
            }

            override fun onFinish() {
                var intent: Intent
                if (StartActivity.workoutExercise.size == StartActivity.position) {
                    val toast = Toast.makeText(applicationContext, "Тренировка завершена", Toast.LENGTH_SHORT)
                    toast.show()
                    intent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(intent)
                } else if (!StartActivity.workoutExercise[StartActivity.position].timeCheck) {
                    intent = Intent(applicationContext, RepeatViewerActivity::class.java)
                    startActivity(intent)
                } else if (StartActivity.workoutExercise[StartActivity.position].timeCheck) {
                    intent = Intent(applicationContext, TimeViewerActivity::class.java)
                    startActivity(intent)
                }
                var mediaPlayer: MediaPlayer? = MediaPlayer.create(applicationContext, R.raw.bell)
                mediaPlayer?.start()
                finish()
            }
        }.start()
        supportActionBar?.hide()
    }

    fun stopPause(view: View) {
        if (StartActivity.workoutExercise.size == StartActivity.position) {
            val toast = Toast.makeText(applicationContext, "Тренировка завершена", Toast.LENGTH_SHORT)
            toast.show()
            intent = Intent(applicationContext, MenuActivity::class.java)
            StartActivity.position = 0
            startActivity(intent)
        } else if (!StartActivity.workoutExercise[StartActivity.position].timeCheck) {
            intent = Intent(applicationContext, RepeatViewerActivity::class.java)
            startActivity(intent)
        } else if (StartActivity.workoutExercise[StartActivity.position].timeCheck) {
            intent = Intent(applicationContext, TimeViewerActivity::class.java)
            startActivity(intent)
        }
        close = true
        finish()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Отменить тренировку")
            .setMessage("Вы уверены, что хотите отменить тренировку?")
            .setPositiveButton("OK") { dialog, which ->
                super.onBackPressed()
                StartActivity.series = 0
                close = true
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
