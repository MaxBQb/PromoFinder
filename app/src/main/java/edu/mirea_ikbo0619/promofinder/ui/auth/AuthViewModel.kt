package edu.mirea_ikbo0619.promofinder.ui.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mirea_ikbo0619.promofinder.UserPreference
import org.koin.android.annotation.KoinViewModel

abstract class AuthViewModel : ViewModel() {
    val isSignInSelected = ObservableBoolean(true)
    val username = ObservableField("")
    val password = ObservableField("")
    val password2 = ObservableField("")
    val isPasswordVisible = ObservableField(false)
    val email = ObservableField("")
    abstract val isAuthorized: LiveData<Boolean>
    abstract val isAnonymous: LiveData<Boolean>
    abstract val wasAuthorized: Boolean

    fun togglePasswordVisibility() {
        isPasswordVisible.set(!isPasswordVisible.get()!!)
    }
    abstract fun handleWasAuthorized(): Boolean
    abstract fun signInAnonymous(): Boolean
    abstract fun signIn(): Boolean
    abstract fun signUp(): Boolean
    abstract fun signOut()
}

@KoinViewModel
class AuthViewModelImpl(
    private val settings: UserPreference
): AuthViewModel() {
    override val isAuthorized = MutableLiveData(false)
    override val wasAuthorized get() = settings.wasAuthorized
    override val isAnonymous = MutableLiveData(settings.isAnonymous)

    private fun authorize() {
        settings.wasAuthorized = true
        isAnonymous.postValue(settings.isAnonymous)
        isAuthorized.postValue(true)
    }

    override fun handleWasAuthorized(): Boolean {
        authorize()
        return true
    }

    override fun signInAnonymous(): Boolean {
        settings.token = ""
        authorize()
        return true
    }

    override fun signIn(): Boolean {
        settings.token = "123"
        authorize()
        return true
    }

    override fun signUp(): Boolean {
        settings.token = "123"
        authorize()
        return true
    }

    override fun signOut() {
        settings.wasAuthorized = false
        settings.token = ""
        isAuthorized.postValue(false)
    }
}