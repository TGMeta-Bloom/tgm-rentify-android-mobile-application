package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tgmrentify.R
import com.example.tgmrentify.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {

            // 1. Splash Delay
            delay(2000)

            // 2. Determine Next Activity
            val nextActivityClass = withContext(Dispatchers.IO) {
                val sharedPrefs = SharedPreferencesHelper(this@SplashActivity)

                if (sharedPrefs.isOnboardingCompleted()) {
                    // If onboarding is done, go to Role Selection (NOT Login)
                    RoleSelectionActivity::class.java
                } else {
                    // If first time, go to Onboarding
                    OnboardingActivity::class.java
                }
            }

            // 3. Navigate
            startActivity(Intent(this@SplashActivity, nextActivityClass))
            finish()
        }
    }
}