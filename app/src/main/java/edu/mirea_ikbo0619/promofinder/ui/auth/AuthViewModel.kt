package edu.mirea_ikbo0619.promofinder.ui.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    val isSignInSelected = ObservableBoolean(true)
    val username = ObservableField("")
    val password = ObservableField("")
    val password2 = ObservableField("")
    val isPasswordVisible = ObservableField(false)
    val email = ObservableField("")

    fun togglePasswordVisibility() {
        isPasswordVisible.set(!isPasswordVisible.get()!!)
    }
}