package com.bvbsaha.fitnesskursach.menu

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.database.Exercise
import com.bvbsaha.fitnesskursach.database.Workout
import com.bvbsaha.fitnesskursach.database.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    //создание workouviewmodel и чтение данных из бд
    //задержа на 7 секуд
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcom)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        workoutViewModel = ViewModelProviders.of(this).get(WorkoutViewModel::class.java)
        Handler().postDelayed({
            val homeIntent = Intent(this@MainActivity, MenuActivity::class.java)
            startActivity(homeIntent)
            workoutViewModel.insert(Workout(80,"Руки","Сложно"))
            workoutViewModel.insert(Exercise(80,80,"Отжимания","Сложно","",4,true,13,"seconds",0,5,"seconds",false))
            workoutViewModel.insert(Workout(81,"Ноги","Сложно"))
            workoutViewModel.insert(Exercise(81,81,"Приседания","Легко","",4,true,13,"seconds",0,5,"seconds",false))
            workoutViewModel.insert(Workout(82,"Пресс","Сложно"))
            workoutViewModel.insert(Exercise(82,82,"Скручивания","Сложно","",4,true,13,"seconds",0,5,"seconds",false))
            workoutViewModel.insert(Workout(83,"Разминка","Легко"))
            workoutViewModel.insert(Exercise(83,83,"Разминка шеи","Легко","",4,true,13,"seconds",0,5,"seconds",false))
            finish()
        }, 700)
    }

    companion object {
        lateinit var workoutViewModel: WorkoutViewModel
    }
}
