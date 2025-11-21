package com.example.tgmrentify.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tgmrentify.databinding.ItemMyPostBinding
import com.example.tgmrentify.model.Post

class MyPostsAdapter(private val onDeleteClick: (Post) -> Unit) :
    ListAdapter<Post, MyPostsAdapter.MyPostViewHolder>(PostDiffCallback()) {

    class MyPostViewHolder(private val binding: ItemMyPostBinding, val onDeleteClick: (Post) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.tvCaption.text = post.caption
            binding.btnHelpfulCount.text = "${post.helpfulCount} Helpful"
            binding.btnDelete.setOnClickListener { onDeleteClick(post) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val binding = ItemMyPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPostViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    }
}