package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.R

class ForgotPasswordConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_confirm)

        val tvBackToLogin = findViewById<TextView>(R.id.tv_back_to_login)
        val btnHeaderBack = findViewById<ImageView>(R.id.btn_header_back)
        
        // For testing purposes: Click the "Process Started" text to simulate clicking the email link
        val tvProcessStarted = findViewById<TextView>(R.id.tv_process_started)
        
        tvBackToLogin.setOnClickListener {
            // Navigate back to Login and clear stack
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        
        btnHeaderBack.setOnClickListener {
            // Navigate back to Login (or finish current activity to go back to request)
            finish()
        }

        tvProcessStarted.setOnClickListener {
            // Simulating the user clicking the link in their email
            val intent = Intent(this, ForgotPasswordResetActivity::class.java)
            startActivity(intent)
        }

        // Automatically simulate redirect to Reset Password screen after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ForgotPasswordResetActivity::class.java)
            startActivity(intent)
            // Optionally finish this activity if you want the back button from Reset to go to Login/Request
            // finish() 
        }, 3000) // 3 second delay
    }
}