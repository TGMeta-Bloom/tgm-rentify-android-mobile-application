package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.R
import com.google.android.material.textfield.TextInputEditText

class ForgotPasswordResetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_reset)

        val etNewPassword = findViewById<TextInputEditText>(R.id.input_new_password)
        val etRepeatPassword = findViewById<TextInputEditText>(R.id.input_repeat_password)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvBackToLogin = findViewById<TextView>(R.id.tv_back_to_login)
        val btnHeaderBack = findViewById<ImageView>(R.id.btn_header_back)

        btnSave.setOnClickListener {
            val newPassword = etNewPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            if (newPassword.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != repeatPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navigate to Success Screen
            val intent = Intent(this, ForgotPasswordSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvBackToLogin.setOnClickListener {
            // Navigate back to Login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        
        btnHeaderBack.setOnClickListener {
            // Typically navigating back from Reset would go to Login or close the "link" flow
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}