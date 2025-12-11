package com.example.tgmrentify.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordPostPropertyFormBinding
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.viewModel.LandlordViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream
import java.util.UUID

class LandlordPostPropertyFormFragment : Fragment() {

    private var _binding: FragmentLandlordPostPropertyFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LandlordViewModel by viewModels()
    
    private var selectedImageUri: Uri? = null

    // Launcher for taking a picture (returns a thumbnail Bitmap)
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            binding.ivPropertyImagePreview.setImageBitmap(bitmap)
            // Convert Bitmap to Uri for upload
            selectedImageUri = getImageUri(requireContext(), bitmap)
        }
    }

    // Launcher for picking an image from the gallery (returns a Uri)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            binding.ivPropertyImagePreview.setImageURI(uri)
            selectedImageUri = uri
        }
    }

    // Permission Launcher
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            takePictureLauncher.launch(null)
        } else {
            Toast.makeText(requireContext(), "Camera permission is required to take photos.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordPostPropertyFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupListeners()
        observeViewModel()
    }

    private fun setupSpinner() {
        val propertyTypes = listOf("Apartment", "House", "Villa", "Annex")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, propertyTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPropertyType.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPostProperty.setOnClickListener {
            if (validateForm()) {
                savePropertyData()
            } else {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAddImage.setOnClickListener {
            showImageSourceDialog()
        }
    }
    
    private fun observeViewModel() {
        viewModel.isProcessing.observe(viewLifecycleOwner) { isLoading ->
            binding.btnPostProperty.isEnabled = !isLoading
            binding.btnPostProperty.text = if (isLoading) "Posting..." else "Post Property"
        }

        viewModel.operationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                viewModel.clearSuccess()
                findNavController().navigate(R.id.action_LandlordPostPropertyFormFragment_to_LandlordPostPropertySuccessFragment)
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun savePropertyData() {
        val title = binding.etPropertyTitle.text.toString().trim()
        val description = binding.etPropertyDescription.text.toString().trim()
        val district = binding.etPropertyDistrict.text.toString().trim()
        val priceStr = binding.etPropertyPrice.text.toString().trim()
        val contact = binding.etContactNumber.text.toString().trim()
        val type = binding.spinnerPropertyType.selectedItem.toString()
        val rentAmount = priceStr.toDoubleOrNull() ?: 0.0
        
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        if (currentUserId.isEmpty()) {
            Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        val propertyId = UUID.randomUUID().toString()

        if (selectedImageUri != null) {
            // Upload Image First
            viewModel.uploadImage(selectedImageUri!!, propertyId) { downloadUrl ->
                if (downloadUrl != null) {
                    val property = Property(
                        propertyId = propertyId,
                        landlordId = currentUserId,
                        title = title,
                        description = description,
                        location = district,
                        rentAmount = rentAmount,
                        propertyType = type,
                        status = "Available",
                        contactNumber = contact,
                        imageUrls = listOf(downloadUrl)
                    )
                    viewModel.saveProperty(property, isNew = true)
                } else {
                    Toast.makeText(context, "Failed to upload image. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Save without image
            val property = Property(
                propertyId = propertyId,
                landlordId = currentUserId,
                title = title,
                description = description,
                location = district,
                rentAmount = rentAmount,
                propertyType = type,
                status = "Available",
                contactNumber = contact,
                imageUrls = emptyList()
            )
            viewModel.saveProperty(property, isNew = true)
        }
    }

    private fun validateForm(): Boolean {
        val title = binding.etPropertyTitle.text.toString().trim()
        val description = binding.etPropertyDescription.text.toString().trim()
        val district = binding.etPropertyDistrict.text.toString().trim()
        val price = binding.etPropertyPrice.text.toString().trim()
        val contact = binding.etContactNumber.text.toString().trim()

        return title.isNotEmpty() && description.isNotEmpty() && district.isNotEmpty() && price.isNotEmpty() && contact.isNotEmpty()
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Camera", "Gallery")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Image Source")
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
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                takePictureLauncher.launch(null)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(requireContext(), "Camera permission is needed to take property photos.", Toast.LENGTH_LONG).show()
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    
    // Helper to get Uri from Bitmap (Not recommended for production high-res, but works for thumbnail fix)
    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}