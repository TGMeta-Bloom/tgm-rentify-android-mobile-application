package com.example.tgmrentify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tgmrentify.model.Post
import com.example.tgmrentify.repository.PostRepository

class FeedViewModel : ViewModel() {

    // Instantiate the repository. (Later, we can use Hilt for dependency injection)
    private val repository = PostRepository()

    // Private MutableLiveData that can be changed within this class
    private val _feedPosts = MutableLiveData<List<Post>>()

    // Public LiveData that the Fragment can observe (but not change)
    val feedPosts: LiveData<List<Post>> = _feedPosts

    // Function to load the data
    fun loadFeed() {
        val posts = repository.getFeedPosts()
        _feedPosts.value = posts
    }
}