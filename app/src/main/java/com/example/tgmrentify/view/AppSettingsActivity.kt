package com.example.tgmrentify.view

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.tgmrentify.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial

class AppSettingsActivity : AppCompatActivity() {

    companion object {
        const val THEME_PREF = "THEME_PREF"
        const val PREF_KEY = "is_dark_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_settings)

        val btnBack: ImageView = findViewById(R.id.btn_back_settings)
        val switchTheme: SwitchMaterial = findViewById(R.id.switch_theme)
        val btnAbout: LinearLayout = findViewById(R.id.btn_about_app)
        
        val tvThemeLight: TextView = findViewById(R.id.tv_theme_light)
        val tvThemeDark: TextView = findViewById(R.id.tv_theme_dark)

        val sharedPreferences = getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)

        btnBack.setOnClickListener {
            finish()
        }

        // Determine current theme mode to set the switch state correctly on launch
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isSystemDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES
        
        // Check saved pref, otherwise fallback to system
        val isDarkMode = sharedPreferences.getBoolean(PREF_KEY, isSystemDarkMode)
        switchTheme.isChecked = isDarkMode

        // Set initial text colors
        updateThemeLabels(switchTheme.isChecked, tvThemeLight, tvThemeDark)

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Update colors immediately
            updateThemeLabels(isChecked, tvThemeLight, tvThemeDark)
            
            // Save Preference
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREF_KEY, isChecked)
            editor.apply()
            
            // Apply the Theme to the whole app
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // About Dialog - User Friendly Material Dialog
        btnAbout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("About TGMRentify")
                .setMessage("TGMRentify v1.0.0\n\nDeveloped for MAD Module.\n\nYour trusted partner for finding the perfect rental property.")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun updateThemeLabels(isDarkMode: Boolean, tvLight: TextView, tvDark: TextView) {
        val activeColor = ContextCompat.getColor(this, R.color.app_blue)
        // Use Theme's Primary Text Color instead of hardcoded Black for better Dark Mode support
        val inactiveColor = getThemeColor(android.R.attr.textColorPrimary)

        if (isDarkMode) {
            // Dark Mode Active: Dark text is Blue, Light text is Inactive
            tvDark.setTextColor(activeColor)
            tvLight.setTextColor(inactiveColor)
        } else {
            // Light Mode Active: Light text is Blue, Dark text is Inactive
            tvLight.setTextColor(activeColor)
            tvDark.setTextColor(inactiveColor)
        }
    }

    private fun getThemeColor(attr: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }
}