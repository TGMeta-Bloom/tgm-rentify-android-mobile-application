@file:Suppress("SpellCheckingInspection")

package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.NavGraphDirections
import com.example.tgmrentify.databinding.FragmentLandlordDashboardBinding
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.model.PropertyType
import com.example.tgmrentify.view.adapter.LandlordGridAdapter
import com.example.tgmrentify.view.adapter.PropertyTypeAdapter
import com.example.tgmrentify.viewModel.LandlordViewModel
import com.google.android.material.snackbar.Snackbar

class LandlordDashboardFragment : Fragment() {

    private var _binding: FragmentLandlordDashboardBinding? = null
    private val binding get() = _binding!!

    // Updated: Use simple initialization since ViewModel has a default constructor
    private val viewModel: LandlordViewModel by viewModels()

    private lateinit var propertyTypeAdapter: PropertyTypeAdapter
    private lateinit var landlordGridAdapter: LandlordGridAdapter

    private val propertyTypes = mutableListOf(
        PropertyType("All", true),
        PropertyType("Apartment"),
        PropertyType("House"),
        PropertyType("Villa"),
        PropertyType("Annex")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupListeners()
        observeViewModel()
        // loadInitialData() - Removed because ViewModel loads data automatically on init
    }

    private fun setupAdapters() {
        // 1. Property Type Filters
        propertyTypeAdapter = PropertyTypeAdapter { selectedType ->
            handleTypeFilter(selectedType)
        }
        binding.recyclerViewFilters.apply {
            adapter = propertyTypeAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            propertyTypeAdapter.submitList(propertyTypes.toList())
        }

        // 2. Landlord Properties Grid
        landlordGridAdapter = LandlordGridAdapter { property ->
            // Navigation Trigger
            navigateToPropertyDetails(property)
        }
        binding.recyclerViewLandlordPropertiesGrid.apply {
            adapter = landlordGridAdapter
        }
    }

    private fun setupListeners() {
        // Define any other listeners here
    }

    private fun observeViewModel() {
        // Updated: Observe 'landlordProperties' instead of 'properties'
        viewModel.landlordProperties.observe(viewLifecycleOwner) { properties ->
            landlordGridAdapter.submitList(properties)
        }

        // Optional: Handle loading/errors if you want
        /*
        viewModel.isProcessing.observe(viewLifecycleOwner) { isLoading ->
             // Show/Hide progress
        }
        */
    }

    private fun handleTypeFilter(selectedType: PropertyType) {
        val updatedList = propertyTypes.map {
            it.copy(isSelected = it.name == selectedType.name)
        }
        propertyTypes.clear()
        propertyTypes.addAll(updatedList)
        propertyTypeAdapter.submitList(propertyTypes.toList())

        // In a real app, you would filter the list in the ViewModel
        Snackbar.make(binding.root, "Filter: ${selectedType.name}", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToPropertyDetails(property: Property?) {
        if (property == null) return

        try {
            // Using GLOBAL ACTION via NavGraphDirections to fix "Destination not found" issues
            val action = NavGraphDirections.actionGlobalLandlordDetails(property)
            findNavController().navigate(action)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(binding.root, "Nav Error: ${e.message}", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}