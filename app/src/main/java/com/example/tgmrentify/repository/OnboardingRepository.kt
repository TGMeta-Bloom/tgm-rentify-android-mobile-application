// com.example.tgmrentify.repository.OnboardingRepository.kt
package com.example.tgmrentify.repository

import com.example.tgmrentify.model.OnboardingItem
import com.example.tgmrentify.utils.SharedPreferencesHelper

class OnboardingRepository(private val prefsHelper: SharedPreferencesHelper) {



    fun checkOnboardingStatus(): Boolean {
        return prefsHelper.isOnboardingCompleted()
    }

    fun markOnboardingAsCompleted() {
        prefsHelper.setOnboardingCompleted(true)
    }
}