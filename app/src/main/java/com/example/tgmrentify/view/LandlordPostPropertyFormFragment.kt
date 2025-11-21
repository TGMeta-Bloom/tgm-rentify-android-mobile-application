package com.example.tgmrentify.view

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordPostPropertyFormBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LandlordPostPropertyFormFragment : Fragment() {

    private var _binding: FragmentLandlordPostPropertyFormBinding? = null
    private val binding get() = _binding!!

    // Launcher for taking a picture (returns a thumbnail Bitmap)
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            binding.ivPropertyImagePreview.setImageBitmap(bitmap)
        }
    }

    // Launcher for picking an image from the gallery (returns a Uri)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            binding.ivPropertyImagePreview.setImageURI(uri)
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
    }

    private fun setupSpinner() {
        // Example property types
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
                // Navigate to the success screen
                findNavController().navigate(R.id.action_LandlordPostPropertyFormFragment_to_LandlordPostPropertySuccessFragment)
            } else {
                // Show popup message
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnAddImage.setOnClickListener {
            showImageSourceDialog()
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
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> takePictureLauncher.launch(null) // Camera
                    1 -> pickImageLauncher.launch("image/*") // Gallery
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}