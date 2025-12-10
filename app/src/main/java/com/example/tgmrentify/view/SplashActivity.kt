package com.example.tgmrentify.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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

        if (sharedPreferences.contains("is_dark_mode")) {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // === Animation Logic ===
        val logo = findViewById<ImageView>(R.id.iv_app_logo)
        val appName = findViewById<TextView>(R.id.tv_app_name)

        // Set initial state
        logo.alpha = 0f
        logo.scaleX = 0.5f
        logo.scaleY = 0.5f
        logo.translationY = 50f

        appName.alpha = 0f
        appName.translationY = 50f

        // Animate Logo (Fade In + Scale Up + Slide Up)
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .translationY(0f)
            .setDuration(1200)
            .start()

        // Animate Text (Fade In + Slide Up) with slight delay
        appName.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()

        // Navigation Logic
        lifecycleScope.launch {
            delay(2500) // Increased delay slightly to let animation finish nicely

            val nextActivityClass = withContext(Dispatchers.IO) {
                val sharedPrefs = SharedPreferencesHelper(this@SplashActivity)
                if (sharedPrefs.isOnboardingCompleted()) {
                    RoleSelectionActivity::class.java
                } else {
                    OnboardingActivity::class.java
                }
            }

            startActivity(Intent(this@SplashActivity, nextActivityClass))
            finish()
        }
    }
}