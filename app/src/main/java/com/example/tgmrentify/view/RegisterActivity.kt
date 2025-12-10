package com.example.tgmrentify.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.tgmrentify.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class RegisterActivity : AppCompatActivity() {

    private var userRole: String? = null
    private var imageUri: Uri? = null
    private lateinit var profileImageView: CircleImageView

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            imageUri?.let {
                Glide.with(this).load(it).into(profileImageView)
            }
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            Glide.with(this).load(it).into(profileImageView)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to take a picture", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userRole = intent.getStringExtra("selected_role")

        profileImageView = findViewById(R.id.image_profile)
        val etEmail = findViewById<EditText>(R.id.input_email_register)
        val etPassword = findViewById<EditText>(R.id.input_password_register)
        val etConfirmPassword = findViewById<EditText>(R.id.input_confirm_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        val btnCamera = findViewById<ImageButton>(R.id.btn_camera)

        // Set up the city dropdown
        val cities = resources.getStringArray(R.array.city_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        val cityDropdown = findViewById<AutoCompleteTextView>(R.id.autocomplete_city)
        cityDropdown.setAdapter(adapter)

        btnCamera.setOnClickListener {
            showImageSourceDialog()
        }

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val selectedCity = cityDropdown.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Email must be a @gmail.com address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedCity.isEmpty() || selectedCity == getString(R.string.city_area)) {
                Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUri == null) {
                Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Implement registration logic with Firebase Auth & Storage
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        AlertDialog.Builder(this)
            .setTitle("Select Profile Picture")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermissionAndOpenCamera()
                    1 -> pickImage.launch("image/*")
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val photoFile = File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            getExternalFilesDir(null)
        )
        val newImageUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            photoFile
        )
        imageUri = newImageUri
        takePicture.launch(newImageUri)
    }
}