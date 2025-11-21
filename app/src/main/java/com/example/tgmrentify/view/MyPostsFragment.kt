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
import com.example.tgmrentify.databinding.DialogDeletePostBinding
import com.example.tgmrentify.databinding.FragmentMyPostsBinding
import com.example.tgmrentify.model.Post
import com.example.tgmrentify.view.adapter.MyPostsAdapter
import java.util.UUID

class MyPostsFragment : Fragment() {
    private var _binding: FragmentMyPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyPostsAdapter
    private var myPostsList = mutableListOf<Post>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        adapter = MyPostsAdapter { post -> showDeleteDialog(post) }
        binding.rvMyPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyPosts.adapter = adapter

        loadDummyData()
    }

    private fun showDeleteDialog(post: Post) {
        val dialogBinding = DialogDeletePostBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnConfirmDelete.setOnClickListener {
            myPostsList.remove(post)
            adapter.submitList(myPostsList.toList())
            dialog.dismiss()
        }
        dialogBinding.btnCancelDelete.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun loadDummyData() {
        myPostsList = mutableListOf(
            Post(UUID.randomUUID().toString(), "Me", "", "Now", "", "Post 1 Caption", "helpful", 5),
            Post(UUID.randomUUID().toString(), "Me", "", "1h", "", "Post 2 Caption", "helpful", 10)
        )
        adapter.submitList(myPostsList.toList())
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}