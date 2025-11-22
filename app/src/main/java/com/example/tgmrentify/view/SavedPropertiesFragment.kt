package com.example.tgmrentify.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tgmrentify.databinding.DialogRemovePropertyBinding
import com.example.tgmrentify.databinding.FragmentSavedPropertiesBinding
import com.example.tgmrentify.view.adapter.SavedPropertiesAdapter
import com.example.tgmrentify.model.SavedProperty
import java.util.UUID

class SavedPropertiesFragment : Fragment() {

    private var _binding: FragmentSavedPropertiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SavedPropertiesAdapter
    private var savedList = mutableListOf<SavedProperty>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Toolbar Navigation
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // 2. Setup Adapter
        adapter = SavedPropertiesAdapter { property ->
            showRemoveDialog(property)
        }

        // 3. Setup RecyclerView (Grid Layout with 2 columns)
        binding.rvSavedProperties.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSavedProperties.adapter = adapter

        // 4. Load Dummy Data
        loadDummyData()
    }

    private fun showRemoveDialog(property: SavedProperty) {
        val dialogBinding = DialogRemovePropertyBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnConfirmRemove.setOnClickListener {
            savedList.remove(property)
            adapter.submitList(savedList.toList())
            dialog.dismiss()
        }
        dialogBinding.btnCancelRemove.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun loadDummyData() {
        savedList = mutableListOf(
            SavedProperty(UUID.randomUUID().toString(), "Cozy Apartment in Colombo 7", "Colombo"),
            SavedProperty(UUID.randomUUID().toString(), "Luxury Villa near Beach", "Galle"),
            SavedProperty(UUID.randomUUID().toString(), "Modern House for Rent", "Kandy"),
            SavedProperty(UUID.randomUUID().toString(), "Small Annex for Students", "Nugegoda"),
            SavedProperty(UUID.randomUUID().toString(), "Office Space in City", "Colombo"),
            SavedProperty(UUID.randomUUID().toString(), "Holiday Bungalow", "Nuwara Eliya")
        )
        adapter.submitList(savedList.toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}