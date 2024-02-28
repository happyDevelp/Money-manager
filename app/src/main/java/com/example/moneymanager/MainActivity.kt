package com.example.moneymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moneymanager.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      /*  tabLayout = binding.tabLayout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_graph) as NavHostFragment
        val navController = navHostFragment.navController
        val viewPager = binding.viewPager

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabTitles[position]
        }

        tabLayout.setupWithViewPager(viewPager)*/



    }
}