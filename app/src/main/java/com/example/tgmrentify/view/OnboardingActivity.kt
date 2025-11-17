package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.tgmrentify.R
import com.example.tgmrentify.model.OnboardingItem
import com.example.tgmrentify.utils.SharedPreferencesHelper

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnAction: Button
    private lateinit var sharedPrefs: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.view_pager_onboarding)
        btnAction = findViewById(R.id.btn_onboarding_action)
        sharedPrefs = SharedPreferencesHelper(this)

        val onboardingItems = listOf(
            OnboardingItem(
                imageResId = R.drawable.img_onboarding_1,
                title = "Find Your Dream Home",
                description = "Explore thousands of rentals nearby, simple, fast, and stress-free with TGM Rentify."
            ),
            OnboardingItem(
                imageResId = R.drawable.img_onboarding_2,
                title = "Browse, Compare & Manage",
                description = "View listings, compare homes, and manage your properties — all in one app with TGM Rentify."
            )
        )

        val adapter = OnboardingPagerAdapter(onboardingItems)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboardingItems.size - 1) {
                    btnAction.text = "Get Started"
                } else {
                    btnAction.text = "Next"
                }
            }
        })

        btnAction.setOnClickListener {
            if (viewPager.currentItem + 1 < adapter.itemCount) {
                viewPager.currentItem += 1
            } else {
                sharedPrefs.setOnboardingCompleted(true)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
