package com.example.tgmrentify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgmrentify.model.Property
import com.example.tgmrentify.repository.LandlordRepository
import kotlinx.coroutines.launch

// Note: For simplicity, we are instantiating the repository directly.
// In a production app, use Dependency Injection (like Hilt/Koin).
class LandlordViewModel(
    private val repository: LandlordRepository = LandlordRepository()
) : ViewModel() {

    // LiveData to hold the list of properties for the UI to observe
    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>> = _properties

    // Simulate the ID of the logged-in user
    private val currentLandlordId = "LANDLORD_123"

    init {
        // Automatically load properties when the ViewModel is created
        loadLandlordProperties()
    }

    fun loadLandlordProperties() {
        viewModelScope.launch {
            try {
                // Fetch data from the repository (simulating Firestore call)
                val propertyList = repository.getLandlordProperties(currentLandlordId)
                _properties.value = propertyList
            } catch (e: Exception) {
                // Handle error (e.g., set an error LiveData)
                e.printStackTrace()
                _properties.value = emptyList()
            }
        }
    }
}