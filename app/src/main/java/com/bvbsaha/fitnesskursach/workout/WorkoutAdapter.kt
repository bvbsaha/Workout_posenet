package com.bvbsaha.fitnesskursach.workout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.Workout
import com.bvbsaha.fitnesskursach.workout.WorkoutViewActivity
import kotlinx.android.synthetic.main.activity_view.*


class WorkoutAdapter(context: Context) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {
    private var mInflater = LayoutInflater.from(context)
    private lateinit var mWorkout: List<Workout>
    private lateinit var cardView: CardView
    var id: Int = 0



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): WorkoutViewHolder {
        val itemView = mInflater.inflate(R.layout.workout_item, parent, false)
        cardView = itemView.findViewById(R.id.cardView)

        return WorkoutViewHolder(itemView, this)

    }

    //заполнение данных из mworkout

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: WorkoutViewHolder, positon: Int) {
        var currentStinrg = mWorkout[positon].title
        holder.id = mWorkout[positon].id
        holder.mWorkoutTitle.text = currentStinrg
        currentStinrg = mWorkout[positon].description
        holder.mWorkoutDescription.text = currentStinrg


    }


    override fun getItemCount(): Int {
        return if (::mWorkout.isInitialized) {
            mWorkout.size
        } else {
            0
        }
    }


    inner class WorkoutViewHolder(viewItem: View, workoutAdapter: WorkoutAdapter) : RecyclerView.ViewHolder(viewItem),
        View.OnClickListener {
        var mAdapter: WorkoutAdapter = workoutAdapter
        var id: Int = this@WorkoutAdapter.id
        var mWorkoutTitle: TextView = viewItem.findViewById(R.id.workout_title)
        var mWorkoutDescription: TextView = viewItem.findViewById(R.id.workout_description)

        init {
            viewItem.setOnClickListener(this)
        }



        override fun onClick(view: View) {
            var intentView = Intent(itemView.context, WorkoutViewActivity::class.java)
            intentView.putExtra(ID, id)
            itemView.context.startActivity(intentView)

        }


    }


    fun setList(list: List<Workout>) {
        mWorkout = list
        this.notifyDataSetChanged()
    }

    companion object {
        var ID: String = "com.bvbsaha.fitnesskursach.menu.id"
    }
}