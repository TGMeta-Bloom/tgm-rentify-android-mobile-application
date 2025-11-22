package com.example.tgmrentify.model

import java.io.Serializable

// Level 3: The actual content
data class LibraryArticle(
    val title: String,
    val body: String
) : Serializable

// Level 1: The Main Topic Card containing a list of articles
data class LibraryCategory(
    val title: String,
    val colorHex: String,
    val iconResId: Int, // Let's add an icon for extra flair!
    val articles: List<LibraryArticle>
)