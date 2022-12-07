package com.bvbsaha.fitnesskursach.exercise

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat.getColor
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.Exercise
import com.bvbsaha.fitnesskursach.database.Workout
import com.bvbsaha.fitnesskursach.menu.MainActivity
import com.bvbsaha.fitnesskursach.workout.WorkoutViewActivity
import com.bvbsaha.fitnesskursach.workout.WorkoutViewActivity.Companion.workoutId
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.workout_item.*



class ExerciseAdapter(context: Context) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private lateinit var cardView: CardView
    private var mInflater = LayoutInflater.from(context)
    lateinit var mExercise: List<Exercise>
    var id: Int = 0


    @SuppressLint("ResourceAsColor")
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ExerciseViewHolder {
        val itemView = mInflater.inflate(R.layout.exercise_item, parent, false)
        cardView = itemView.findViewById(R.id.cardView)

        return ExerciseViewHolder(itemView, this)
    }

   //заполенение данных из mExercise в item

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        var currentString = mExercise[position].title
        holder.mExerciseTitle.text = currentString
        currentString = mExercise[position].description
        holder.mExerciseDescription.text = currentString
        holder.id = mExercise[position].id
        holder.done = mExercise[position].done
        if (holder.done ){
            cardView.setCardBackgroundColor(R.color.colorAccent)
        }
        if (mExercise[position].timeCheck) {
            when (mExercise[position].timeFormat) {
                "секунды" -> currentString = "сек"
                else -> currentString = "мин"
            }
            holder.mExerciseDetails.text =
                "Подходы: ${mExercise[position].series} Время: ${mExercise[position].time} $currentString"
        } else {
            holder.mExerciseDetails.text = "Подходы: ${mExercise[position].series} Повторения: ${mExercise[position].repeat}"
        }
        when (mExercise[position].pauseFormat) {
            "секунды" -> currentString = "сек"
            else -> currentString = "мин"
        }
        holder.mExerciseDetails.text =
            "${holder.mExerciseDetails.text} Пауза: ${mExercise[position].pause} $currentString"
        holder.id = mExercise[position].id

    }

    override fun getItemCount(): Int {
        return if (::mExercise.isInitialized) {
            mExercise.size
        } else {
            0
        }
    }


    inner class ExerciseViewHolder(viewItem: View, exerciseAdapter: ExerciseAdapter) :
        RecyclerView.ViewHolder(viewItem), View.OnClickListener {
        var exerciseAdapter = exerciseAdapter
        var mExerciseTitle: TextView = viewItem.findViewById(R.id.exercise_title)
        var mExerciseDescription: TextView = viewItem.findViewById(R.id.exercise_desctiption)
        var mExerciseDetails: TextView = viewItem.findViewById(R.id.detailsExercise)
        var id: Int = this@ExerciseAdapter.id
        var done:Boolean = false
        init {
            viewItem.setOnClickListener(this)
        }

       //запускает новый интент по нажатию и передает туда ID
        override fun onClick(view: View) {
            if (id >= 80){
                val toast = Toast.makeText(itemView.context,"Вы не можете редактировать стандартные упражнения",Toast.LENGTH_SHORT)
                toast.show()

            }else {
                val intentView: Intent = Intent(itemView.context, ExerciseViewActivity::class.java)
                intentView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intentView.putExtra(ID, id)
                itemView.context.startActivity(intentView)
            }
        }
    }

    //заполняет лист новыми данными
    fun setList(list: List<Exercise>) {
        var tmp: ArrayList<Exercise> = ArrayList()
        list.forEach {
            if (it.workoutId == WorkoutViewActivity.workoutId) {
                tmp.add(it)
            }

        }
        mExercise = tmp
        this.notifyDataSetChanged()
    }

    companion object {
        const val ID = "com.bvbsaha.fitnesskursach.ExerciseId"
    }

}