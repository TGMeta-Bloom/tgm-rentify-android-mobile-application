package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordPostPropertySuccessBinding

class LandlordPostPropertySuccessFragment : Fragment() {

    private var _binding: FragmentLandlordPostPropertySuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordPostPropertySuccessBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            // Navigate to the Add Property screen
            findNavController().navigate(R.id.action_LandlordPostPropertySuccessFragment_to_LandlordAddPropertyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}