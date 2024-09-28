package com.codebyashish.foodui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.codebyashish.foodui.databinding.ActivityMainBinding
import com.codebyashish.foodui.fragments.FoodCartFragment
import com.codebyashish.foodui.fragments.HomeFragment
import com.codebyashish.foodui.utitlity.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var index : Int? = 0
    private val TAG = "MainActivityLogs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        binding.bottomMenu.setOnNavigationItemSelectedListener(this);

        index = intent.getStringExtra("index")?.toInt() ?: 0

        Log.d(TAG, "onCreate: $index")
        Log.d(TAG, "onCreate: ${intent.getStringExtra("index")}")

        if (savedInstanceState == null) {
            setHomeFragment(index)
        }

        window.statusBarColor = resources.getColor(R.color.white, null)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR





    }

    private fun setHomeFragment(index: Int?) {
        var fragment= Fragment()
        if (index == 0) {
            fragment = HomeFragment()
        } else if (index == 2) {
            fragment = FoodCartFragment()
        }
        if (index != null) {
            binding.bottomMenu.selectedItemId = when (index) {
                0 -> R.id.nav_home
                2 -> R.id.nav_cart
                else -> R.id.nav_home
            }
        } else {
            binding.bottomMenu.selectedItemId = R.id.nav_home
        }

        openFragment(fragment)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        val fragmentContainer = supportFragmentManager.findFragmentById(R.id.container)
        return when (item.itemId) {
            R.id.nav_home -> {
                if (fragmentContainer is HomeFragment){

                } else {
                    fragment = HomeFragment()
                    openFragment(fragment)
                }
                true
            }

            R.id.nav_search -> {
                SharedPref.clearSharedPreferences(this)
                true
            }

            R.id.nav_cart -> {
                if (fragmentContainer is FoodCartFragment){

                } else {
                    fragment = FoodCartFragment()
                    openFragment(fragment)
                }
                true
            }
            else -> false
        }
    }


    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.container, fragment, "")
            .commitAllowingStateLoss()
    }

}