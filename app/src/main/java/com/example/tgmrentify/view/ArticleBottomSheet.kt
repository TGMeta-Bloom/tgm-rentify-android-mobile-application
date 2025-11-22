package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tgmrentify.databinding.BottomSheetArticleBinding
import com.example.tgmrentify.model.LibraryArticle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArticleBottomSheet(private val article: LibraryArticle) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvArticleTitle.text = article.title
        binding.tvArticleBody.text = article.body

        binding.btnClose.setOnClickListener {
            dismiss() // Close the sheet
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}