package com.bvbsaha.fitnesskursach.menu

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.WorkoutViewModel

/** class HomeFragment is for this display layout R.layout.fragment_home
 *  also it reads old data to CardView
 *  @property workoutAdapter is object WorkoutAdapter for RecycleView
 *  @see WorkoutAdapter
 *  @author Mateusz Karłowski
 */

class HomeFragment : Fragment() {
    private lateinit var workoutAdapter: WorkoutAdapter

    /**
     * onCreateView find recyclerView set adapter and LayoutManager
     * finally get data from database and set as data for RecycleView
     * @return view with recycleView
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        MainActivity.workoutViewModel = ViewModelProviders.of(this).get(WorkoutViewModel::class.java)
        workoutAdapter = WorkoutAdapter(this.requireContext())
        MainActivity.workoutViewModel.allWorkout.observe(this, Observer { words ->
            words?.let { workoutAdapter.setList(it) }
        })
        recyclerView.adapter = workoutAdapter
        return view
    }
}