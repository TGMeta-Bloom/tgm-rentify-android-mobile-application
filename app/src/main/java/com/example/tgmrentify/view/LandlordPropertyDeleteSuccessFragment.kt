package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordPropertyDeleteSuccessBinding

class LandlordPropertyDeleteSuccessFragment : Fragment() {

    private var _binding: FragmentLandlordPropertyDeleteSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordPropertyDeleteSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            // Navigate to Landlord Add Property (Dashboard) screen
            findNavController().navigate(R.id.action_LandlordPropertyDeleteSuccessFragment_to_LandlordAddPropertyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}