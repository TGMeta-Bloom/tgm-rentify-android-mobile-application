package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordAddPropertyBinding

class LandlordAddPropertyFragment : Fragment() {

    private var _binding: FragmentLandlordAddPropertyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fabAddProperty.setOnClickListener {
            findNavController().navigate(R.id.action_LandlordAddPropertyFragment_to_LandlordPostPropertyFormFragment)
        }
        binding.fabViewProperties.setOnClickListener {
            // Navigate to Landlord Properties Fragment (My Properties)
            findNavController().navigate(R.id.action_LandlordAddPropertyFragment_to_LandlordPropertiesFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}