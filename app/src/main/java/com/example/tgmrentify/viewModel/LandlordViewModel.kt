package com.example.tgmrentify.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.repository.LandlordRepository
import kotlinx.coroutines.launch

class LandlordViewModel : ViewModel() {

    private val repository = LandlordRepository()

    // LiveData for the list of properties
    val landlordProperties: LiveData<List<Property>> = repository.getLandlordProperties()

    // Loading State
    private val _isProcessing = MutableLiveData<Boolean>()
    val isProcessing: LiveData<Boolean> get() = _isProcessing

    // Error Handling
    private val _errorEvent = MutableLiveData<String?>()
    val errorEvent: LiveData<String?> get() = _errorEvent

    // Success Event (optional, for navigation or toasts)
    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean> get() = _operationSuccess

    /**
     * Add or Update a Property
     */
    fun saveProperty(property: Property, isNew: Boolean) {
        _isProcessing.value = true
        viewModelScope.launch {
            try {
                if (isNew) {
                    repository.addProperty(property)
                } else {
                    repository.updateProperty(property)
                }
                _operationSuccess.value = true
                _errorEvent.value = null // Clear any previous errors
            } catch (e: Exception) {
                _errorEvent.value = "Failed to save property: ${e.message}"
                _operationSuccess.value = false
            } finally {
                _isProcessing.value = false
            }
        }
    }

    /**
     * Delete a Property
     */
    fun deleteProperty(propertyId: String) {
        _isProcessing.value = true
        viewModelScope.launch {
            try {
                repository.deleteProperty(propertyId)
                _operationSuccess.value = true
                _errorEvent.value = null
            } catch (e: Exception) {
                _errorEvent.value = "Failed to delete property: ${e.message}"
                _operationSuccess.value = false
            } finally {
                _isProcessing.value = false
            }
        }
    }

    /**
     * Upload Image and return the download URL via callback (or handle internally)
     * For simplicity, this function uses a callback lambda for the result URL,
     * but you could also use another LiveData.
     */
    fun uploadImage(uri: Uri, propertyId: String, onResult: (String?) -> Unit) {
        _isProcessing.value = true
        viewModelScope.launch {
            try {
                val downloadUrl = repository.uploadPropertyImage(uri, propertyId)
                onResult(downloadUrl)
                _errorEvent.value = null
            } catch (e: Exception) {
                _errorEvent.value = "Image upload failed: ${e.message}"
                onResult(null)
            } finally {
                _isProcessing.value = false
            }
        }
    }

    // Helper to reset events after they are consumed by the UI
    fun clearError() {
        _errorEvent.value = null
    }

    fun clearSuccess() {
        _operationSuccess.value = false
    }
}