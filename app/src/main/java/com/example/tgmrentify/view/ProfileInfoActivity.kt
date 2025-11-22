package com.example.tgmrentify.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.tgmrentify.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ProfileInfoActivity : AppCompatActivity() {

    private lateinit var ivProfilePhoto: CircleImageView
    private var tempImageUri: Uri? = null

    // Launcher for Gallery
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { handleImageSelection(it) }
    }

    // Launcher for Camera
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && tempImageUri != null) {
            handleImageSelection(tempImageUri!!)
        }
    }

    // Launcher for Camera Permission
    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to take photos.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)

        // Initialize Views
        val btnBack = findViewById<ImageView>(R.id.btn_back_info)
        val btnSave = findViewById<Button>(R.id.btn_save_info)
        val spinnerCity = findViewById<Spinner>(R.id.spinner_city)
        val btnEditPhoto = findViewById<ImageView>(R.id.btn_edit_photo_info)
        ivProfilePhoto = findViewById(R.id.image_profile_info)

        // Setup City Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.city_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapter

        // Back Button Logic
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Edit Photo Logic
        btnEditPhoto.setOnClickListener {
            showImageSourceDialog()
        }

        ivProfilePhoto.setOnClickListener {
            showImageSourceDialog()
        }

        // Save Button Logic
        btnSave.setOnClickListener {
            // Here you would trigger the DB Update Operation
            Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
            finish() // Go back to previous screen
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        MaterialAlertDialogBuilder(this)
            .setTitle("Select Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermissionAndLaunch()
                    1 -> pickImageLauncher.launch("image/*")
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndLaunch() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "Camera permission is needed to take profile photos.", Toast.LENGTH_LONG).show()
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun launchCamera() {
        try {
            val tempFile = File.createTempFile("temp_image", ".jpg", cacheDir)
            tempImageUri = FileProvider.getUriForFile(
                this,
                "$packageName.provider",
                tempFile
            )
            // Safe unwrap using let block
            tempImageUri?.let { takePictureLauncher.launch(it) }
        } catch (e: Exception) {
            Toast.makeText(this, "Error launching camera: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleImageSelection(uri: Uri) {
        // Update UI with selected image
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.ic_default_profile)
            .into(ivProfilePhoto)
            
        // NOTE: In a real app, you would save this 'uri' to a ViewModel or upload it to your server here/on Save.
    }
}