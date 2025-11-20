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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.DialogDeletePostBinding
import com.example.tgmrentify.databinding.FragmentMyPostsBinding
import com.example.tgmrentify.model.Post
import com.example.tgmrentify.view.adapter.MyPostsAdapter
import java.util.UUID

class MyPostsFragment : Fragment() {

    private var _binding: FragmentMyPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyPostsAdapter

    // Temporary local list to simulate deletion
    private var myPostsList = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Setup Toolbar
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // 2. Setup Adapter
        adapter = MyPostsAdapter { postToDelete ->
            showDeleteConfirmationDialog(postToDelete)
        }

        // 3. Setup RecyclerView
        binding.rvMyPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyPosts.adapter = adapter

        // 4. Load Dummy Data
        loadDummyData()
    }

    private fun showDeleteConfirmationDialog(post: Post) {
        val dialogBinding = DialogDeletePostBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        // Make background transparent so our rounded CardView shows correctly
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnConfirmDelete.setOnClickListener {
            // Perform delete
            myPostsList.remove(post)
            adapter.submitList(myPostsList.toList()) // Update adapter
            dialog.dismiss()
        }

        dialogBinding.btnCancelDelete.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun loadDummyData() {
        myPostsList = mutableListOf(
            Post(UUID.randomUUID().toString(), "Me", "", "Just now", "", "New supermarket opened near City Road, convenient for tenants nearby", "helpful", 12),
            Post(UUID.randomUUID().toString(), "Me", "", "1h ago", "", "Always check water and electricity before signing the rental agreement.", "helpful", 12),
            Post(UUID.randomUUID().toString(), "Me", "", "2h ago", "", "Does anyone know if houses on Main Street allow pets?", "question", 5)
        )
        adapter.submitList(myPostsList.toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}