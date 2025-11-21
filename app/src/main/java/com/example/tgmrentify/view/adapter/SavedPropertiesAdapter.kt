package com.example.tgmrentify.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tgmrentify.databinding.ItemSavedPropertyBinding

// IMPORT THE MODEL YOU CREATED (This is the fix)
import com.example.tgmrentify.model.SavedProperty

// ❌ DELETE THIS LINE: data class SavedProperty(...)

class SavedPropertiesAdapter(private val onRemoveClick: (SavedProperty) -> Unit) :
    ListAdapter<SavedProperty, SavedPropertiesAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemSavedPropertyBinding, val onRemoveClick: (SavedProperty) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavedProperty) {
            binding.tvPropertyTitle.text = item.title
            binding.tvPropertyCity.text = "City: ${item.city}"
            binding.btnRemove.setOnClickListener { onRemoveClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSavedPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onRemoveClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<SavedProperty>() {
        override fun areItemsTheSame(oldItem: SavedProperty, newItem: SavedProperty) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SavedProperty, newItem: SavedProperty) = oldItem == newItem
    }
}