package com.kh.mo.shopyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kh.mo.shopyapp.databinding.ActivityMainBinding
import com.kh.mo.shopyapp.remote.service.Network
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(
            this,R.layout.activity_main
        )
        setUpBottomNavigationView()


    }

    private fun setUpBottomNavigationView() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        controller = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, controller)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    controller.navigate(R.id.homeFragment)
                    true
                }
                R.id.favourite -> {
                    controller.navigate(R.id.favoritesFragment)
                    true
                }
                R.id.search -> {
                    controller.navigate(R.id.searchFragment)
                    true
                }
                R.id.cart -> {
                    controller.navigate(R.id.cartFragment)
                    true
                }
                R.id.profile -> {
                    controller.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    }


