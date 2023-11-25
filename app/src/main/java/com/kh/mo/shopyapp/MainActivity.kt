package com.kh.mo.shopyapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kh.mo.shopyapp.databinding.ActivityMainBinding
import com.kh.mo.shopyapp.utils.makeGone
import com.kh.mo.shopyapp.utils.makeVisible

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
        disappearAndShowBottomNavigation()

    }

    fun  disappearAndShowBottomNavigation(){

        controller.addOnDestinationChangedListener { _, navDestination, _ ->
            if (navDestination.id == R.id.homeFragment ||
                navDestination.id == R.id.cartFragment ||
                navDestination.id == R.id.searchFragment ||
                navDestination.id == R.id.favoritesFragment ||
                navDestination.id == R.id.profileFragment ) {
                binding.bottomNavigation.makeVisible()
            } else {
                binding.bottomNavigation.makeGone()
            }
        }
    }


}