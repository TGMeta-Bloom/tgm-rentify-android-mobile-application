package com.example.tgmrentify

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tgmrentify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Navigation Controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2. Connect the Bars
        binding.bottomNavigation.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)

        // 3. Dynamic Setup
        setupUserInterface()
    }

    private fun setupUserInterface() {
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val userRole = sharedPref.getString("user_role", "tenant") // Default

        if (userRole == "tenant") {
            setupTenantDesign()
        } else {
            setupLandlordDesign()
        }
    }

    private fun setupTenantDesign() {
        val navView = binding.navView
        // Apply Blue Background + Image (defined in drawable)
        navView.setBackgroundResource(R.drawable.nav_drawer_background)
        // Load Layouts
        navView.inflateHeaderView(R.layout.nav_header_main)
        navView.inflateMenu(R.menu.nav_drawer_menu)
        // Set Colors
        val white = ContextCompat.getColorStateList(this, R.color.white)
        navView.itemIconTintList = white
        navView.itemTextColor = white
    }

    private fun setupLandlordDesign() {
        binding.navView.setBackgroundColor(getColor(android.R.color.white))
    }
}