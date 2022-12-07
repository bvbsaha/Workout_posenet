package com.bvbsaha.fitnesskursach.menu

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.exercise.CreateExerciseActivity
import com.bvbsaha.fitnesskursach.workout.CreateWorkoutActivity
import com.bvbsaha.fitnesskursach.weather.WeatherFragment
import com.bvbsaha.fitnesskursach.workout.WorkoutFragment



class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

   //меняет фрагмент в зависимости от выбора

    lateinit var fab: FloatingActionButton
    private val newWorkoutActivityRequestCode = 1
    lateinit var fragment: Fragment


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                fragment = WorkoutFragment()
                fab.hide()
            }
            R.id.navigation_history -> {
                fragment = WeatherFragment()
                fab.hide()
            }
            R.id.navigation_settings -> {
                fragment = BmiFragment()
                fab.hide()
            }
            else -> {
                var creatorIntent: Intent = Intent(this, CreateWorkoutActivity::class.java)
                startActivityForResult(creatorIntent, newWorkoutActivityRequestCode)
                fab.hide()

            }
        }
        return loadFragment(fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        fragment = WorkoutFragment()
        loadFragment(fragment)
        fab = findViewById(R.id.floatingActionButton)
        fab.hide()
        fab.setOnClickListener {
            if (fragment is WorkoutFragment) {
                var creatorIntent: Intent = Intent(this, CreateExerciseActivity::class.java)
                startActivityForResult(creatorIntent, newWorkoutActivityRequestCode)
            }
        }
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
        supportActionBar?.hide()
    }


    //запускает фрагмент в части лайаута

   private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

}
