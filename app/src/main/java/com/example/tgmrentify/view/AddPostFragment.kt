package com.example.tgmrentify.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.databinding.FragmentAddPostBinding

class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    // Variable to store the selected image URI
    private var selectedImageUri: Uri? = null

    // 1. Launcher to Open Gallery
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            // Show the selected image in the UI
            binding.ivImagePlaceholder.setImageURI(uri)
            binding.ivImagePlaceholder.imageTintList = null // Remove the grey tint
            binding.tvAddPictureLabel.text = "Change Photo" // Update text
        }
    }

    // 2. Launcher to Request Permissions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Check if permission was granted
        val isGranted = permissions.entries.all { it.value }
        if (isGranted) {
            openGallery()
        } else {
            Toast.makeText(requireContext(), "Permission Denied. Cannot access photos.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back Button Logic
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // "Add Photo" Click Logic
        binding.cardImageUpload.setOnClickListener {
            checkPermissionsAndOpenGallery()
        }

        // "Post Now" Logic
        binding.btnPostNow.setOnClickListener {
            val caption = binding.etCaption.text.toString()
            if (caption.isBlank()) {
                Toast.makeText(requireContext(), "Please write something!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Posting...", Toast.LENGTH_SHORT).show()
                // Later: We will save this data to the list
                findNavController().navigateUp()
            }
        }
    }

    private fun checkPermissionsAndOpenGallery() {
        // Determine which permission to ask for based on Android Version
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES // Android 13+
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE // Android 12 and below
        }

        // Check if we already have it
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            // Request it
            requestPermissionLauncher.launch(arrayOf(permission))
        }
    }

    private fun openGallery() {
        // Launch the gallery picker for images
        pickImageLauncher.launch("image/*")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}