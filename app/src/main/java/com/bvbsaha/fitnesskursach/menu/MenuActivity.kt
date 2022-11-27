package com.bvbsaha.fitnesskursach.menu

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.bvbsaha.fitnesskursach.R
import com.bvbsaha.fitnesskursach.creator.SetTitleActivity

/**
 * A class MenuActivity is Main Activity in my App
 * @author Mateusz Karłowski
 */


class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * Function onNavigationItemSelected change fragments when user chosen option on BottomNavigationView
     */

    lateinit var fab: FloatingActionButton
    private val newWorkoutActivityRequestCode = 1
    lateinit var fragment: Fragment

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                fragment = HomeFragment()
                fab.show()
            }
            R.id.navigation_history -> {
                fragment = WeatherFragment()
                fab.hide()
            }
            R.id.navigation_settings -> {
                fragment = SettingsFragment()
                fab.hide()
            }
            else -> {
                fab.show()
                fragment = HomeFragment()
            }
        }
        return loadFragment(fragment)
    }

    /**
     * Function onCreate evokes when class MenuActivity are creates.
     * It sets layout activity_menu
     * It evokes function loadFragment because set default fragment
     * Next create variable navigation and changes it into BottomNavigationView from layout
     * The last set listener for BottomNavigationView
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        fragment = HomeFragment()
        loadFragment(fragment)
        fab = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            if (fragment is HomeFragment) {
                var creatorIntent: Intent = Intent(this, SetTitleActivity::class.java)
                startActivityForResult(creatorIntent, newWorkoutActivityRequestCode)
            }
        }
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
        supportActionBar?.hide()
    }

    /**
     * Function loadFragment start new Fragment in the part of the Layout
     */

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
