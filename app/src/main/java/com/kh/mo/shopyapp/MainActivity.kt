package com.kh.mo.shopyapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.kh.mo.shopyapp.databinding.ActivityMainBinding
import com.kh.mo.shopyapp.utils.createDialog
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

    private fun  disappearAndShowBottomNavigation(){

        controller.addOnDestinationChangedListener { _, navDestination, _ ->
            if (navDestination.id == R.id.homeFragment ||
                navDestination.id == R.id.cartFragment ||
                navDestination.id == R.id.searchResultFragment ||
                navDestination.id == R.id.favoritesFragment ||
                navDestination.id == R.id.profileFragment ) {
                binding.bottomNavigation.makeVisible()
            } else {
                binding.bottomNavigation.makeGone()
            }
        }
    }

    override fun onBackPressed() {

        if (controller.currentDestination?.id == R.id.signInFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }


    fun checkIsLogin(isLogin: Boolean){
        Log.d("TAG", "checkIsLogin:$isLogin ")
        if(isLogin){
            NavigationUI.setupWithNavController(binding.bottomNavigation, controller)
        }else{
            controlToBottomNavigationClicks()
        }
    }

     private fun controlToBottomNavigationClicks() {
    binding.bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->

        val itemId = item.itemId
        if (itemId == R.id.favoritesFragment || itemId == R.id.cartFragment) {
            showRequestLoginDialog()
            return@setOnNavigationItemSelectedListener false
        }
        onNavDestinationSelected(item, controller)
    }
}
    fun showRequestLoginDialog() {
        createDialog(context = this,
            title=getString(R.string.please_login),
            message = getString(R.string.gust_message),
            sure = {controller.navigate(R.id.signInFragment)}, cancel = {})

    }

}