package com.example.tgmrentify.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tgmrentify.databinding.ItemLibraryCategoryBinding
import com.example.tgmrentify.model.LibraryArticle
import com.example.tgmrentify.model.LibraryCategory
import com.google.android.material.chip.Chip

class LibraryCategoryAdapter(
    private val categories: List<LibraryCategory>,
    private val onArticleClick: (LibraryArticle) -> Unit
) : RecyclerView.Adapter<LibraryCategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: ItemLibraryCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemLibraryCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        // 1. Set Card Details
        holder.binding.tvCategoryTitle.text = category.title
        // Use a real icon if you have one, or a default
        holder.binding.ivCategoryIcon.setImageResource(category.iconResId)

        try {
            holder.binding.cardRoot.setCardBackgroundColor(Color.parseColor(category.colorHex))
        } catch (e: Exception) {
            holder.binding.cardRoot.setCardBackgroundColor(Color.WHITE)
        }

        // 2. GENERATE CHIPS DYNAMICALLY (The cool part!)
        holder.binding.chipGroupArticles.removeAllViews() // Clear old chips

        for (article in category.articles) {
            val chip = createChip(holder.itemView.context, article)
            holder.binding.chipGroupArticles.addView(chip)
        }
    }

    private fun createChip(context: Context, article: LibraryArticle): Chip {
        val chip = Chip(context)
        chip.text = article.title
        chip.setOnClickListener { onArticleClick(article) }
        // Style the chip to look nice (White background inside colored card)
        chip.setChipBackgroundColorResource(android.R.color.white)
        return chip
    }

    override fun getItemCount() = categories.size
}