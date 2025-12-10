package com.example.tgmrentify.view

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.R
import com.google.android.material.button.MaterialButton

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val btnBack: ImageView = findViewById(R.id.btn_back_change_pw)
        val btnVerify: MaterialButton = findViewById(R.id.btn_verify_password)
        val btnSave: MaterialButton = findViewById(R.id.btn_save_password)
        val tvForgot: TextView = findViewById(R.id.tv_forgot_current_password)

        val etCurrentPassword = findViewById<EditText>(R.id.et_current_password)
        val etNewPassword = findViewById<EditText>(R.id.et_new_password)
        val etConfirmPassword = findViewById<EditText>(R.id.et_confirm_password)

        // Apply underline to "Forgot Password" text programmatically
        tvForgot.paintFlags = tvForgot.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnBack.setOnClickListener {
            finish()
        }
        

        btnVerify.setOnClickListener {
            val currentPassword = etCurrentPassword.text.toString().trim()

            if (currentPassword.isEmpty()) {
                Toast.makeText(this, "Please enter your current password", Toast.LENGTH_SHORT).show()
            } else {
                // Mock Verification Success
                Toast.makeText(this, "Current password verified successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        btnSave.setOnClickListener {
            val newPassword = etNewPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mock Save Success
            Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_LONG).show()

            // Navigate to Role Selection Screen
            val intent = Intent(this, RoleSelectionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        tvForgot.setOnClickListener {
            val intent = Intent(this, ForgotPasswordRequestActivity::class.java)
            startActivity(intent)
        }
    }
}