package com.example.tgmrentify.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tgmrentify.databinding.ItemFeedPostBinding
import com.example.tgmrentify.model.Post
// Import your R file if necessary, e.g.: import com.example.rentsmartlite.R
// You will also need an image loading library like Glide or Coil
// import com.bumptech.glide.Glide

class FeedAdapter : ListAdapter<Post, FeedAdapter.PostViewHolder>(PostDiffCallback()) {

    /**
     * ViewHolder class: Holds the view for a single item.
     */
    class PostViewHolder(private val binding: ItemFeedPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.tvUserName.text = post.userName
            binding.tvTimestamp.text = post.timestamp
            binding.tvCaption.text = post.caption
            binding.chipTag.text = post.tag
            binding.chipHelpfulCount.text = "${post.helpfulCount} Helpful"

            // --- Image Loading ---
            // You need an image loading library like Glide or Coil for this.
            // Example using Glide:
            /*
            Glide.with(binding.root.context)
                .load(post.userAvatarUrl)
                .placeholder(R.drawable.ic_profile_placeholder) // Add a placeholder drawable
                .circleCrop()
                .into(binding.ivUserAvatar)

            Glide.with(binding.root.context)
                .load(post.postImageUrl)
                .placeholder(R.drawable.ic_image_placeholder) // Add a placeholder drawable
                .into(binding.ivPostImage)
            */
        }
    }

    /**
     * Called when RecyclerView needs a new ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemFeedPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    /**
     * DiffUtil: Helps the adapter efficiently update the list
     * by calculating the differences between the old and new lists.
     */
    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}