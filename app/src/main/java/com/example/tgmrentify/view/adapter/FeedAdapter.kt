package com.example.tgmrentify.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tgmrentify.R
import com.example.tgmrentify.databinding.ItemFeedPostBinding
import com.example.tgmrentify.model.Post

class FeedAdapter : ListAdapter<Post, FeedAdapter.PostViewHolder>(PostDiffCallback()) {

    inner class PostViewHolder(private val binding: ItemFeedPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.tvAuthorName.text = post.userName
            binding.tvPostTime.text = post.timestamp
            binding.tvPostContent.text = post.caption
            binding.chipHelpfulCount.text = "${post.helpfulCount} Helpful"

            // Handle "More Options" (The 3 Dots)
            val moreOptionsButton = binding.root.findViewById<android.view.View>(R.id.btn_more_options)

            moreOptionsButton?.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)

                // Only add "Hide Post"
                popup.menu.add("Hide Post")

                popup.setOnMenuItemClickListener { menuItem ->
                    if (menuItem.title == "Hide Post") {
                        // Hide Logic
                        val newList = currentList.toMutableList()
                        newList.remove(post)
                        submitList(newList) // Update the screen

                        Toast.makeText(view.context, "Post Hidden", Toast.LENGTH_SHORT).show()
                        true
                    } else {
                        false
                    }
                }
                popup.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemFeedPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}