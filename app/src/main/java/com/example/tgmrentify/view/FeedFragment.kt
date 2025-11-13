package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
// Import the 'by viewModels' delegate
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.databinding.FragmentFeedBinding
import com.example.tgmrentify.viewModel.FeedViewModel
import com.example.tgmrentify.view.adapter.FeedAdapter


class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    // UN-comment this line. It will work now!
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

        // Set up the observer
        observeViewModel()

        // Tell the ViewModel to load the data
        viewModel.loadFeed()

        binding.btnAddPost.setOnClickListener {
            // Handle "Add Post" button click
        }
    }

    private fun setupRecyclerView() {
        feedAdapter = FeedAdapter()
        binding.rvFeed.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }
    }

    // UN-comment and implement this function
    private fun observeViewModel() {
        // Observe the 'feedPosts' LiveData
        viewModel.feedPosts.observe(viewLifecycleOwner) { posts ->
            // When the data changes, submit the new list to the adapter
            posts?.let {
                feedAdapter.submitList(it)
            }
        }
    }

    // We don't need this temporary function anymore!
    // private fun loadTemporaryData() { ... }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}