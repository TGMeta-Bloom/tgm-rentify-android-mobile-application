package com.example.tgmrentify.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tgmrentify.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AccountDeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_delete)

        val btnBack: ImageView = findViewById(R.id.btn_back_delete)
        val btnDelete: MaterialButton = findViewById(R.id.btn_confirm_delete)

        btnBack.setOnClickListener {
            finish()
        }

        btnDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Account?")
            .setMessage("Are you sure you want to permanently delete your account? This action cannot be undone and all your data will be lost.")
            .setIcon(R.drawable.ic_error)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                // Mock Deletion Success
                Toast.makeText(this, "Account deleted successfully.", Toast.LENGTH_LONG).show()
                
                // Navigate to Login or Onboarding
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .show()
    }
}