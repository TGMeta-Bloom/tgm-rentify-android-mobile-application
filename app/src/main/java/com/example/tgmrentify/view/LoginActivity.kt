package com.example.tgmrentify.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.MainActivity
import com.example.tgmrentify.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private var userRole: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userRole = intent.getStringExtra("selected_role")

        val etEmail = findViewById<TextInputEditText>(R.id.input_email)
        val etPassword = findViewById<TextInputEditText>(R.id.input_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvRegister = findViewById<TextView>(R.id.text_create_one)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        val tvForgotPassword = findViewById<TextView>(R.id.text_forgot_password)

        btnBack.setOnClickListener {
            finish() // Goes back to the previous screen
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // TODO: Implement login logic with Firebase Auth
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

            // Save role to SharedPreferences
            val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("user_role", userRole)
                apply()
            }

            // Redirect to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Clear the back stack so the user can't go back to login
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {
                putExtra("selected_role", userRole)
            }
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordRequestActivity::class.java)
            startActivity(intent)
        }
    }
}