package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.R
import com.example.tgmrentify.viewModel.ForgotPasswordViewModel
import com.google.android.material.textfield.TextInputEditText

class ForgotPasswordRequestActivity : AppCompatActivity() {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_request)

        val etEmail = findViewById<TextInputEditText>(R.id.input_email)
        val btnSend = findViewById<Button>(R.id.btn_send)
        val tvBackToLogin = findViewById<TextView>(R.id.tv_back_to_login)
        val btnHeaderBack = findViewById<ImageView>(R.id.btn_header_back)

        btnSend.setOnClickListener {
            val email = etEmail.text.toString().trim()
            viewModel.sendResetLink(email)
        }

        tvBackToLogin.setOnClickListener {
            finish()
        }
        
        btnHeaderBack.setOnClickListener {
            finish()
        }

        viewModel.resetResult.observe(this) { result ->
            result.onSuccess {
                val intent = Intent(this, ForgotPasswordConfirmActivity::class.java)
                startActivity(intent)
            }.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}