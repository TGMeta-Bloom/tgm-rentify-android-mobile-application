package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordMyPropertiesBinding
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.view.adapter.LandlordPropertiesAdapter
import java.util.UUID

class LandlordMyPropertiesFragment : Fragment() {

    private var _binding: FragmentLandlordMyPropertiesBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupRecyclerView() {
        // Mock Data
        val properties = listOf(
            Property(
                propertyId = UUID.randomUUID().toString(),
                landlordId = "user1",
                title = "Cozy Apartment in Colombo 7",
                description = "3-bedroom apartment with sea view and rooftop gym.",
                location = "Colombo",
                rentAmount = 170000.0,
                propertyType = "Apartment",
                contactNumber = "077-3344556",
                imageUrls = listOf("dummy_url")
            ),
            Property(
                propertyId = UUID.randomUUID().toString(),
                landlordId = "user1",
                title = "Luxury Villa in Kandy",
                description = "Spacious villa with a large garden and pool.",
                location = "Kandy",
                rentAmount = 250000.0,
                propertyType = "Villa",
                contactNumber = "077-1122334",
                imageUrls = listOf("dummy_url_2")
            ),
            Property(
                propertyId = UUID.randomUUID().toString(),
                landlordId = "user1",
                title = "Modern House in Galle",
                description = "Beautiful house near the fort with historical architecture.",
                location = "Galle",
                rentAmount = 120000.0,
                propertyType = "House",
                contactNumber = "071-9988776",
                imageUrls = listOf("dummy_url_3")
            )
        )

        val adapter = LandlordPropertiesAdapter(
            properties,
            onEditClick = { property ->
                // Navigate to Edit Fragment
                findNavController().navigate(R.id.action_LandlordPropertiesFragment_to_LandlordPropertyEditFormFragment)
            },
            onDeleteClick = { property ->
                // Navigate to Delete Confirm Fragment
                findNavController().navigate(R.id.action_LandlordPropertiesFragment_to_LandlordPropertyDeleteConfirmFragment)
            }
        )

        binding.rvProperties.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProperties.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}