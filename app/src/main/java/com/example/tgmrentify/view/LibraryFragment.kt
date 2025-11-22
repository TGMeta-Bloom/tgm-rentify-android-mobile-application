package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentLibraryBinding
import com.example.tgmrentify.model.LibraryArticle
import com.example.tgmrentify.model.LibraryCategory
import com.example.tgmrentify.view.adapter.LibraryCategoryAdapter

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        // --- CREATE THE DATA ---
        val data = listOf(
            LibraryCategory(
                "Plumbing & Water", "#FFAB40", R.drawable.ic_home, // Orange
                listOf(
                    LibraryArticle("Fixing Leaks", "If a tap drips, tighten the packing nut. If it continues, replace the washer. Turn off main water first!"),
                    LibraryArticle("Clogged Drains", "Use a plunger first. If that fails, try a mix of baking soda and vinegar."),
                    LibraryArticle("Low Pressure", "Check if the aerator on the tap is blocked by debris.")
                )
            ),
            LibraryCategory(
                "Electricity & Power", "#69F0AE", R.drawable.ic_grid, // Green
                listOf(
                    LibraryArticle("Changing Bulbs", "Ensure the switch is off. Use LED bulbs to save money."),
                    LibraryArticle("Tripped Breaker", "Go to the fuse box. Look for the switch that is 'down' or in the middle. Flip it off, then fully on."),
                    LibraryArticle("Saving Energy", "Unplug devices when not in use.")
                )
            ),
            LibraryCategory(
                "Legal & Agreements", "#448AFF", R.drawable.ic_profile, // Blue
                listOf(
                    LibraryArticle("Security Deposit", "Your landlord must return this within 30 days of moving out, minus repairs."),
                    LibraryArticle("Notice Period", "Standard notice is 30 days, but check your specific agreement.")
                )
            )
        )

        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())

        // Pass a function to open the bottom sheet when a chip is clicked
        binding.rvLibrary.adapter = LibraryCategoryAdapter(data) { article ->
            val bottomSheet = ArticleBottomSheet(article)
            bottomSheet.show(parentFragmentManager, "ArticleSheet")
        }

        // --- CRITICAL: TRIGGER THE ANIMATION ---
        binding.rvLibrary.scheduleLayoutAnimation()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}