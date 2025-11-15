package com.example.tgmrentify.model

data class Post(
    val id: String,
    val userName: String,
    val userAvatarUrl: String, // URL or a drawable resource ID
    val timestamp: String, // e.g., "2 minutes ago"
    val postImageUrl: String,
    val caption: String,
    val tag: String, // e.g., "helpful"
    val helpfulCount: Int
)