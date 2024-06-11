package com.example.moneymanager

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moneymanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lockScreenOrientation() // disable rotate screen

        //setup BottomnavigationView
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.transactionFragment -> {
                    navController.navigate(R.id.transactionFragment,null,options)
                }
                R.id.pieChartFragment -> {
                    navController.navigate(R.id.pieChartFragment,null,options)
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment,null,options)
                }
            }
            true
        }

        binding.bottomNavigationView.setOnItemReselectedListener  { item ->
            return@setOnItemReselectedListener
        }



    }

    private fun lockScreenOrientation() {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //lock phone rotation
    }

}