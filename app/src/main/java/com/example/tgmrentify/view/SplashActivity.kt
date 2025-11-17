package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tgmrentify.R
import com.example.tgmrentify.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers // ⚠️ Needed for background I/O
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext // ⚠️ Needed for switching threads

// Import the activities you are navigating to (assuming they are in the .view package)
// IMPORTANT: You should eventually navigate to LoginActivity, not MainActivity, if not logged in.
import com.example.tgmrentify.view.OnboardingActivity
import com.example.tgmrentify.view.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Launch a coroutine for asynchronous operations
        lifecycleScope.launch {

            // 1. Splash Delay (non-blocking)
            delay(2000)

            // 2. Determine Next Activity by checking persistence (I/O)
            val nextActivityClass = withContext(Dispatchers.IO) {
                // Initialize SharedPreferencesHelper inside the background thread
                val sharedPrefs = SharedPreferencesHelper(this@SplashActivity)

                if (sharedPrefs.isOnboardingCompleted()) {
                    // Go to LoginActivity (Your next module) if onboarding is complete
                    LoginActivity::class.java
                } else {
                    // Go to OnboardingActivity if not completed
                    OnboardingActivity::class.java
                }
            }

            // 3. Navigate back on the Main Thread
            startActivity(Intent(this@SplashActivity, nextActivityClass))
            finish()
        }
    }
}
