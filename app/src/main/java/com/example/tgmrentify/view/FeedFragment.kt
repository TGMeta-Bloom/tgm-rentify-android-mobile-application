package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.FragmentFeedBinding
import com.example.tgmrentify.view.adapter.FeedAdapter
import com.example.tgmrentify.viewModel.FeedViewModel

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadFeed()

        binding.btnAddPost.setOnClickListener {
            // This is the navigation code
            // It uses the action ID of the arrow you created in the nav_graph
            findNavController().navigate(R.id.action_feedFragment_to_addPostFragment)
        }
    }

    private fun setupRecyclerView() {
        feedAdapter = FeedAdapter()
        binding.rvFeed.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }
    }

    private fun observeViewModel() {
        // Observe the 'feedPosts' LiveData
        viewModel.feedPosts.observe(viewLifecycleOwner) { posts ->
            // When the data changes, submit the new list to the adapter
            posts?.let {
                feedAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}