package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.databinding.FragmentAddPostBinding

class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Setup Click Listeners ---

        // Handle the toolbar's back arrow click
        binding.toolbar.setNavigationOnClickListener {
            // This will navigate back to the previous screen (TenantFeedFragment)
            findNavController().navigateUp()
        }

        // Handle "Add Picture" click
        binding.ivImagePlaceholder.setOnClickListener {
            // TODO: Launch image picker from gallery or camera
            Toast.makeText(requireContext(), "Add Picture clicked", Toast.LENGTH_SHORT).show()
        }

        // Handle "Post Now" click
        binding.btnPostNow.setOnClickListener {
            val caption = binding.etCaption.text.toString()

            if (caption.isBlank()) {
                binding.tilCaption.error = "Caption cannot be empty"
            } else {
                binding.tilCaption.error = null // Clear error
                // TODO: Send data to a ViewModel to save the new post
                Toast.makeText(requireContext(), "Posting...", Toast.LENGTH_SHORT).show()

                // Navigate back to the feed after "posting"
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}