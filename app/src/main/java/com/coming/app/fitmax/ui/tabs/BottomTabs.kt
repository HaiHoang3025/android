package com.coming.app.fitmax.ui.tabs

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.coming.app.fitmax.R
import com.coming.app.fitmax.databinding.ActivityBottomTabsBinding
import com.coming.app.fitmax.ui.home.HomeFragment
import com.coming.app.fitmax.ui.mission.MissionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class BottomTabs : AppCompatActivity() {


    private lateinit var binding: ActivityBottomTabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_profile,
//                R.id.navigation_mission,
//                R.id.navigation_equiptment,
//                R.id.navigation_profile
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.move_net_tensorflow_fragment -> binding.navView.visibility = View.GONE
                else -> binding.navView.visibility = View.VISIBLE
            }

        }
        navView.setupWithNavController(navController)
    }


}