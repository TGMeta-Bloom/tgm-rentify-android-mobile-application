package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tgmrentify.R

// CHANGE 1: Update the import to the new binding class
import com.example.tgmrentify.databinding.FragmentTenantFeedBinding
import com.example.tgmrentify.view.adapter.FeedAdapter
import com.example.tgmrentify.viewModel.FeedViewModel

class TenantFeedFragment : Fragment() {

    // CHANGE 2: Update the variable type
    private var _binding: FragmentTenantFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // CHANGE 3: Update the inflate call
        _binding = FragmentTenantFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadFeed()

        binding.btnAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addPostFragment)
        }

        binding.btnMenu.setOnClickListener {
            val drawerLayout = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout?.openDrawer(GravityCompat.START)
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
        viewModel.feedPosts.observe(viewLifecycleOwner) { posts ->
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