package com.example.tgmrentify.model

data class SavedProperty(
    val id: String,
    val title: String,
    val city: String,
    val imageUrl: String = "" // Added this for future use
)