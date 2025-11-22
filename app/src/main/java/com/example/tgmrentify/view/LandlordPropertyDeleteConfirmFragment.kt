package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLandlordPropertyDeleteConfirmBinding

class LandlordPropertyDeleteConfirmFragment : Fragment() {

    private var _binding: FragmentLandlordPropertyDeleteConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandlordPropertyDeleteConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCancelDelete.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnConfirmDelete.setOnClickListener {
            // Navigate to Success Screen
            findNavController().navigate(R.id.action_LandlordPropertyDeleteConfirmFragment_to_LandlordPropertyDeleteSuccessFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}