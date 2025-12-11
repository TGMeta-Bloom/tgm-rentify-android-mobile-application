package com.example.tgmrentify.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tgmrentify.repository.LandlordRepository

class LandlordViewModelFactory(private val repository: LandlordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LandlordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Fix: LandlordViewModel uses a default constructor and initializes its own repository.
            // We do not pass 'repository' here anymore to avoid the "Too many arguments" error.
            return LandlordViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
