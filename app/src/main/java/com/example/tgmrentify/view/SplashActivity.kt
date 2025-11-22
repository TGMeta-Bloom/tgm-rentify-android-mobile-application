package com.example.tgmrentify.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.tgmrentify.R
import com.example.tgmrentify.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.example.tgmrentify.view.OnboardingActivity
import com.example.tgmrentify.view.RoleSelectionActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Apply Theme Preference immediately on startup
        val sharedPreferences = getSharedPreferences("THEME_PREF", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)

        // Check if the preference actually exists (user made a choice), otherwise default to Light Mode
        if (sharedPreferences.contains("is_dark_mode")) {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else {
            // Default to Light Mode if no choice made yet
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        lifecycleScope.launch {
            delay(2000)

            val nextActivityClass = withContext(Dispatchers.IO) {
                val sharedPrefs = SharedPreferencesHelper(this@SplashActivity)
                if (sharedPrefs.isOnboardingCompleted()) {
                    // Onboarding complete, go to RoleSelectionActivity
                    RoleSelectionActivity::class.java
                } else {
                    // Onboarding not complete, go to OnboardingActivity
                    OnboardingActivity::class.java
                }
            }

            startActivity(Intent(this@SplashActivity, nextActivityClass))
            finish()
        }
    }
}