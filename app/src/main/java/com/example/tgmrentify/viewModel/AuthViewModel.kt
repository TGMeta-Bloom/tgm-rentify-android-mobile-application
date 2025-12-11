package com.example.tgmrentify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Reverted to simulated logic for Frontend-only development
    private val _resetPasswordResult = MutableLiveData<Result<String>>()
    val resetPasswordResult: LiveData<Result<String>> = _resetPasswordResult

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            // 1. Simulate network delay (loading)
            delay(2000)

            // 2. Check basic email validation
            if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // 3. Return SUCCESS (Simulation)
                _resetPasswordResult.value = Result.success("Password reset email sent (Simulated)")
            } else {
                // 4. Return ERROR (Simulation)
                _resetPasswordResult.value = Result.failure(Exception("Invalid email address"))
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            // 1. Simulate network delay
            delay(2000)

            // 2. Basic validation (Simulated check)
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // In a real app, you would check with Firebase/Backend here
                _loginResult.value = Result.success("Login Successful (Simulated)")
            } else {
                _loginResult.value = Result.failure(Exception("Invalid credentials"))
            }
        }
    }

    fun resetPassword(newPassword: String) {
        // Placeholder for future logic
    }
}