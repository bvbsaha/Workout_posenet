package com.bvbsaha.fitnesskursach.exercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.creator.RepeatActivity
import com.bvbsaha.fitnesskursach.creator.TimeActivity
import com.bvbsaha.fitnesskursach.workout.CreateWorkoutActivity


class CreateExerciseActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup //Кнопки для выбора варианта выполнения упражнений
    lateinit var title: EditText        //Ввод названия нового упражнения
    lateinit var description: EditText  //Ввод сложности упражнения
    lateinit var instruction: EditText  //Ввод инструкции к выполнению упражнения
    private lateinit var spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        radioGroup = findViewById(R.id.radio_group)
        title = findViewById(R.id.exercise_title_edit_text)
        //description = findViewById(R.id.exercise_description_edit_text)
        //спиннер для выбора сложности
        spinner = findViewById(R.id.diffspinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.difficult,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        supportActionBar?.hide()
        instruction = findViewById(R.id.exercise_instruction_edit_text)
    }


    fun nextExercise(view: View) {
        if (!(title.text.toString() == "")) {
            val nextIntent: Intent = if (radioGroup.checkedRadioButtonId == R.id.radio_button_repeat) {
                Intent(this, RepeatActivity::class.java)
            } else {
                Intent(this, TimeActivity::class.java)
            }
            nextIntent.putExtra(TITLE, title.text.toString())
            nextIntent.putExtra(INSTRUCTION, instruction.text.toString())
            nextIntent.putExtra(DESCRIPTION, spinner.selectedItem.toString())
            nextIntent.putExtra(CreateWorkoutActivity.ID, intent.getIntExtra(CreateWorkoutActivity.ID, 0))
            startActivity(nextIntent)
            finish()
        } else if (title.text.toString().length > 30 || description.text.toString().length > 50) {
            val toast =
                Toast.makeText(applicationContext, "Пожалуйста, введите меньшеие значения", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            val toast =
                Toast.makeText(applicationContext, "Пожалуйста, введите данные", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Создание упражнения")
            .setMessage("Вы уверены, что хотите выйти, не сохранив упражнение?")
            .setPositiveButton("ДА") { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton("НЕТ", null)
            .show()
    }

    companion object {
        const val TITLE: String = "com.bvbsaha.fitnesskurasch.etitle"
        const val DESCRIPTION: String = "com.bvbsaha.fitnesskurasch.description"
        const val INSTRUCTION: String = "com.bvbsaha.fitnesskurasch.instrucuton"
    }

}
