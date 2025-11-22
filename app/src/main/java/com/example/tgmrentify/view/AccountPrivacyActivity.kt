package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.R
import com.google.android.material.switchmaterial.SwitchMaterial

class AccountPrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_privacy)

        val btnBack: ImageView = findViewById(R.id.btn_back_privacy)
        val switchVisibility: SwitchMaterial = findViewById(R.id.switch_visibility)
        val tvVisibilityStatus: TextView = findViewById(R.id.tv_visibility_status)
        val btnChangePassword: LinearLayout = findViewById(R.id.btn_change_password)

        // Handle back button click
        btnBack.setOnClickListener {
            finish()
        }

        // Handle switch state change
        switchVisibility.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvVisibilityStatus.text = "Public"
            } else {
                tvVisibilityStatus.text = "Private"
            }
        }

        // Handle Change Password click
        btnChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }
}