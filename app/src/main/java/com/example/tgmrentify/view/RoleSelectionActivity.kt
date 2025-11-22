package com.example.tgmrentify.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.tgmrentify.R
import com.example.tgmrentify.repository.ProfileRepository

class RoleSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        val landlordCard: CardView = findViewById(R.id.card_landlord)
        val tenantCard: CardView = findViewById(R.id.card_tenant)
        val needHelpLayout: LinearLayout = findViewById(R.id.text_need_help)

        landlordCard.setOnClickListener {
            // Update GLOBAL MOCK STATE to Landlord
            ProfileRepository.updateRole("Landlord")
            
            // Navigate to Login/Register
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("selected_role", "Landlord")
            startActivity(intent)
        }

        tenantCard.setOnClickListener {
            // Update GLOBAL MOCK STATE to Tenant
            ProfileRepository.updateRole("Tenant")
            
            // Navigate to Login/Register
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("selected_role", "Tenant")
            startActivity(intent)
        }

        needHelpLayout.setOnClickListener {
            showHelpDialog()
        }
    }

    private fun showHelpDialog() {
        val options = arrayOf("Email Support", "Call Us", "Visit FAQ")

        AlertDialog.Builder(this)
            .setTitle("Need Help?")
            .setIcon(android.R.drawable.ic_menu_help)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> { // Email Support
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:support@rentifyapp.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Help Request - TGM Rentify")
                        }
                        try {
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(this, "No email app found.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    1 -> { // Call Us
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:+94763939423")
                        }
                        try {
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(this, "No phone app found.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    2 -> { // Visit FAQ
                        val url = "https://www.google.com/search?q=rentify+faq" // Placeholder FAQ URL
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(url)
                        }
                        try {
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(this, "No web browser found.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}