package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordMyPropertiesBinding
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.view.adapter.LandlordPropertiesAdapter
import com.example.tgmrentify.viewModel.LandlordViewModel

class LandlordMyPropertiesFragment : Fragment() {


    private var _binding: FragmentLandlordMyPropertiesBinding? = null
    private val binding get() = _binding!!

    // Initialize ViewModel
    private val viewModel: LandlordViewModel by viewModels()
    private lateinit var adapter: LandlordPropertiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordMyPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        // Initialize Adapter with empty list
        adapter = LandlordPropertiesAdapter(
            emptyList(),
            onEditClick = { property ->
                // Navigate to Edit Fragment
                // TODO: Pass property via safe args or bundle if needed
                findNavController().navigate(R.id.action_LandlordPropertiesFragment_to_LandlordPropertyEditFormFragment)
            },
            onDeleteClick = { property ->
                // Navigate to Delete Confirm Fragment
                // TODO: Pass property via safe args or bundle if needed
                findNavController().navigate(R.id.action_LandlordPropertiesFragment_to_LandlordPropertyDeleteConfirmFragment)
            }
        )

        binding.rvProperties.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProperties.adapter = adapter
    }

    private fun observeViewModel() {
        // Observe Real Data from Firestore
        viewModel.landlordProperties.observe(viewLifecycleOwner) { properties ->
            if (properties.isNullOrEmpty()) {
                // Show empty state if needed, or just clear list
                adapter.updateProperties(emptyList())
            } else {
                adapter.updateProperties(properties)
            }
        }

        // Observe Loading State (Optional: Add a ProgressBar to your layout to use this)
        viewModel.isProcessing.observe(viewLifecycleOwner) { isLoading ->
            // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe Errors
        viewModel.errorEvent.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Add button listener if you have a FAB or button to add new property
        // binding.btnAddProperty.setOnClickListener { ... }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}