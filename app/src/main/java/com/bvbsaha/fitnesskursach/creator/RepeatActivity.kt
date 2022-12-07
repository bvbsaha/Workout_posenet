package com.bvbsaha.fitnesskursach.creator

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.Exercise
import com.bvbsaha.fitnesskursach.exercise.CreateExerciseActivity
import com.bvbsaha.fitnesskursach.menu.MainActivity
import com.bvbsaha.fitnesskursach.viewer.StartActivity
import com.bvbsaha.fitnesskursach.workout.CreateWorkoutActivity

//Класс для создания упражнений по числу повторений


class RepeatActivity : AppCompatActivity() {

    lateinit var series: EditText
    lateinit var repeat: EditText
    lateinit var pause: EditText
    private lateinit var spinner: Spinner


    //нахождение элементов макета и сохранение в переменные
    //установка строк из массива в спиннер для выбора времени
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat)
        series = findViewById(R.id.series_repeat)
        repeat = findViewById(R.id.repeat)
        pause = findViewById(R.id.pause_repeat)
        spinner = findViewById(R.id.pauseMenuRepeat)
        ArrayAdapter.createFromResource(
            this,
            R.array.time_formats,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        supportActionBar?.hide()
    }


    //проверки правильности заполнений эдиттекстов
    //



    fun finishRepeat(view: View) {
        if (series.text.toString() == "" || repeat.text.toString() == "" || pause.text.toString() == "") {
            val toast = Toast.makeText(applicationContext, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT)
            toast.show()
        } else if(series.text.toString().toInt() < 1 || repeat.text.toString().toInt() < 1 || pause.text.toString().toInt() < 1){
            val toast = Toast.makeText(applicationContext,"Проверьте введенные данные",Toast.LENGTH_SHORT)
            toast.show()
        }
        else if (series.text.toString().toInt() > 99 ||
            repeat.text.toString().toInt() > 10000 ||
            (pause.text.toString().toInt() > 6000 && spinner.selectedItem == "секунды") ||
            (pause.text.toString().toInt() > 99 && spinner.selectedItem == "минуты")
        ) {
            val toast = Toast.makeText(applicationContext, "Пожалуйста, введите меньшие значения", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            add()
            Log.d("MyLog","упражнение: ${StartActivity.workoutExercise.toString()}")
            finish()
        }
    }

    //добавление упражнения в бд из интента и эдиттекстов
    private fun add() {
        var id: Int = 0
        MainActivity.workoutViewModel.allExercise.observe(this, Observer { words ->
            words?.let { id = it.size }
        })
        MainActivity.workoutViewModel.insert(
            Exercise(
                id,
                intent.getIntExtra(CreateWorkoutActivity.ID, 0),
                intent.getStringExtra(CreateExerciseActivity.TITLE).toString(),
                intent.getStringExtra(CreateExerciseActivity.DESCRIPTION).toString(),
                intent.getStringExtra(CreateExerciseActivity.INSTRUCTION).toString(),
                series.text.toString().toInt(),
                false,
                0,
                "",
                repeat.text.toString().toInt(),
                pause.text.toString().toInt(),
                spinner.selectedItem.toString(),
                false

            )
        )
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Создание Упражнения")
            .setMessage("Вы уверены, что хотите выйти, не сохранив упражнение?")
            .setPositiveButton("ДА") { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton("НЕТ", null)
            .show()
    }
}
