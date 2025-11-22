package com.example.tgmrentify.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "com.example.tgmrentify.prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    /**
     * Sets the status of the onboarding completion.
     * @param completed true if onboarding is completed, false otherwise.
     */
    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply()
    }

    /**
     * Checks if the onboarding has been completed.
     * @return true if onboarding is completed, false otherwise.
     */
    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    /**
     * Clears all data from the shared preferences.
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
