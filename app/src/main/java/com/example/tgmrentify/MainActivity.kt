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

        // 1. Setup Navigation Controller (Standard)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2. Connect the Bars (Standard)
        binding.bottomNavigation.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)

        // 3. DYNAMIC SETUP: Check who is logged in
        setupUserInterface()
    }

    private fun setupUserInterface() {
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        // Default to "tenant" for now so you can see your design
        val userRole = sharedPref.getString("user_role", "tenant")

        if (userRole == "tenant") {
            setupTenantDesign()
        } else {
            setupLandlordDesign()
        }
    }

    private fun setupTenantDesign() {
        val navView = binding.navView

        // --- HERE IS THE MAGIC ---

        // 1. Apply the Background (Blue + Rounded + Image at Bottom)
        // We utilize the file you already created in res/drawable/
        navView.setBackgroundResource(R.drawable.nav_drawer_background)

        // 2. Load Your Header
        navView.inflateHeaderView(R.layout.nav_header_main)

        // 3. Load Your Menu
        navView.inflateMenu(R.menu.nav_drawer_menu)

        // 4. Set Text/Icon Colors to White
        val white = ContextCompat.getColorStateList(this, R.color.white)
        navView.itemIconTintList = white
        navView.itemTextColor = white
    }

    private fun setupLandlordDesign() {
        // Your friend can configure her drawer here later.
        // For now, we leave it blank or set a simple white background.
        binding.navView.setBackgroundColor(getColor(android.R.color.white))
    }
}