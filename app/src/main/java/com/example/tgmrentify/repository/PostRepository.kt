package com.example.tgmrentify.repository

import com.example.tgmrentify.model.Post
import java.util.UUID

class PostRepository {

    // In a real app, this would fetch from a database or network.
    fun getFeedPosts(): List<Post> {
        // We'll just return the same dummy list for now.
        return listOf(
            Post(
                id = UUID.randomUUID().toString(),
                userName = "Gothami Chamodika",
                userAvatarUrl = "url_to_gothami_avatar",
                timestamp = "2 minutes ago",
                postImageUrl = "url_to_supermarket_image",
                caption = "New supermarket opened near City Road, convenient for tenants nearby",
                tag = "helpful",
                helpfulCount = 12
            ),
            Post(
                id = UUID.randomUUID().toString(),
                userName = "Thamasha Nethmini",
                userAvatarUrl = "url_to_thamasha_avatar",
                timestamp = "2 minutes ago",
                postImageUrl = "url_to_vegetable_image",
                caption = "Look at this amazing vegetable from my garden!",
                tag = "community",
                helpfulCount = 8
            )
        )
    }
}