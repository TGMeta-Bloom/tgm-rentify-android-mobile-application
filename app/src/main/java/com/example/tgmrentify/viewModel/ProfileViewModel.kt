package com.example.tgmrentify.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgmrentify.model.User
import com.example.tgmrentify.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()
    val userProfile: LiveData<User?> = repository.userData
    
    private val _updateResult = MutableLiveData<Result<String>>()
    val updateResult: LiveData<Result<String>> = _updateResult

    fun loadProfileData() {
        viewModelScope.launch {
            repository.fetchCurrentUser()
        }
    }

    fun updateProfile(user: User, imageUri: Uri?) {
        viewModelScope.launch {
            val result = repository.updateUserProfile(user, imageUri)
            _updateResult.value = result
        }
    }

    fun updateCoverImage(user: User, imageUri: Uri) {
        viewModelScope.launch {
            val result = repository.updateCoverImage(user, imageUri)
            _updateResult.value = result
        }
    }
    
    fun switchRole() {
        viewModelScope.launch {
            repository.switchRole()
        }
    }
}