package com.bvbsaha.fitnesskursach.workout

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.exercise.CreateExerciseActivity
import com.bvbsaha.fitnesskursach.database.Workout
import com.bvbsaha.fitnesskursach.exercise.ExerciseAdapter
import com.bvbsaha.fitnesskursach.exercise.ExerciseViewActivity
import com.bvbsaha.fitnesskursach.menu.MainActivity
import com.bvbsaha.fitnesskursach.viewer.StartActivity
import com.bvbsaha.fitnesskursach.workout.WorkoutViewActivity.Companion.WORKOUTID
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.exercise_item.*
import kotlinx.android.synthetic.main.exercise_item.view.*
import kotlinx.android.synthetic.main.workout_item.*
import kotlinx.android.synthetic.main.workout_item.cardView
import kotlinx.android.synthetic.main.workout_item.view.*



class WorkoutViewActivity : AppCompatActivity() {
    lateinit var exerciseAdapter: ExerciseAdapter
    var id: Int = 0


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        id = intent.getIntExtra(WorkoutAdapter.ID, 0)
        val workout: Workout = MainActivity.workoutViewModel.findWorkoutById(id)
        val title: TextView = findViewById(R.id.workout_title_view_activity)

        if (id >= 80) {
            button5.setBackgroundColor(getColor(R.color.cardview_shadow_start_color))
            button2.setBackgroundColor(getColor(R.color.cardview_shadow_start_color))

        }

        title.setOnLongClickListener{
            var alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("Введите новое имя тренировки")
            var editText : EditText = EditText(applicationContext)
            alert.setView(editText)
            editText.background.clearColorFilter()
            editText.background.setColorFilter(R.color.primary_material_dark, PorterDuff.Mode.SRC_IN)
            alert.setPositiveButton("OK"){ dialog, which ->
                when {
                    editText.text.toString() == "" -> {
                        val toast =
                            Toast.makeText(applicationContext, "Пожалуйста, введите данные", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    editText.text.toString().length > 30 -> {
                        val toast =
                            Toast.makeText(applicationContext, "Пожалуста, введите меньшие данные", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    else -> {
                        MainActivity.workoutViewModel.updateWorkoutTitle(id, editText.text.toString())
                        title.text = editText.text.toString()
                    }
                }
            }
            alert.setNegativeButton("Отмена", null)
            alert.show()
            return@setOnLongClickListener true
        }

        val description: TextView = findViewById(R.id.workout_description_view_activity)
        description.setOnLongClickListener{
            var alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("Введите новое описание")
            var editText : EditText = EditText(applicationContext)
            alert.setView(editText)
            editText.background.clearColorFilter()
            editText.background.setColorFilter(R.color.primary_material_dark, PorterDuff.Mode.SRC_IN)
            alert.setPositiveButton("OK"){ dialog, which ->
                when {
                    editText.text.toString() == "" -> {
                        val toast =
                            Toast.makeText(applicationContext, "Пожалуйста, введите данные", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    editText.text.toString().length > 30 -> {
                        val toast =
                            Toast.makeText(applicationContext, "Пожалуста, введите меньшие данные", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    else -> {
                        MainActivity.workoutViewModel.updateWorkoutDescription(id, editText.text.toString())
                        description.text = editText.text.toString()
                    }
                }
            }
            alert.setNegativeButton("Отмена", null)
            alert.show()
            return@setOnLongClickListener true
        }
        title.text = workout.title
        description.text = workout.description
        exerciseAdapter = ExerciseAdapter(applicationContext)
        val recyclerView: RecyclerView = findViewById(R.id.recycle_view_exercise)
        workoutId = workout.id
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext)

        MainActivity.workoutViewModel.allExercise.observe(this, Observer { exercise ->
            exercise?.let { exerciseAdapter.setList(it) }
        })
        recyclerView.adapter = exerciseAdapter
        supportActionBar?.hide()
    }

    fun start(view: View) {
        if (exerciseAdapter.mExercise.isNotEmpty()) {
            var startInetnt = Intent(this, StartActivity::class.java)
            startInetnt.putExtra(WORKOUTID, id)
            startActivity(startInetnt)
        } else {
            val toast =
                Toast.makeText(applicationContext, "Добавьте упражнение, чтобы начать", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun deleteWorkout(view: View) {
        if(workoutId >= 80){
            val toast = Toast.makeText(applicationContext,"Вы не можете удалять стандартные тренировки",Toast.LENGTH_SHORT)
            toast.show()
        }else {
            MainActivity.workoutViewModel.deleteById(id)
            finish()
        }
    }
    //создание новой тренировки
    fun newExercise(view: View) {
        if(workoutId >= 80){
            val toast = Toast.makeText(applicationContext,"Вы не можете редактировать стандартные тренировки",Toast.LENGTH_SHORT)
            toast.show()
        }
        else {
            var nextIntent = Intent(this, CreateExerciseActivity::class.java)
            nextIntent.putExtra(CreateWorkoutActivity.ID, id)
            startActivity(nextIntent)
        }
    }

    companion object {
        const val WORKOUTID: String = "com.bvbsaha.fitnesskursach.workoutid"
        var workoutId: Int = 0
    }
}
