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
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordDashboardBinding
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.model.PropertyType
import com.example.tgmrentify.repository.LandlordRepository
import com.example.tgmrentify.view.adapter.LandlordGridAdapter
import com.example.tgmrentify.view.adapter.PropertyTypeAdapter
import com.example.tgmrentify.viewModel.LandlordViewModel
import com.example.tgmrentify.viewModel.LandlordViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LandlordDashboardFragment : Fragment() {

    private var _binding: FragmentLandlordDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LandlordViewModel by viewModels {
        LandlordViewModelFactory(LandlordRepository())
    }

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
        loadInitialData()
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

    private fun loadInitialData() {
        viewModel.loadLandlordProperties()
    }

    private fun setupListeners() {
        // Define any other listeners here
    }

    private fun observeViewModel() {
        viewModel.properties.observe(viewLifecycleOwner) { properties ->
            landlordGridAdapter.submitList(properties)
        }
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
            // Replaced NavGraphDirections with Bundle to bypass unresolved reference
            val bundle = Bundle().apply {
                putParcelable("property", property)
            }
            findNavController().navigate(R.id.actionGlobalLandlordDetails, bundle)
        } catch (e: Exception) {
            // Log the error to Snackbar to debug why it's crashing
            e.printStackTrace()
            Snackbar.make(binding.root, "Nav Error: ${e.message}", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}