package edu.mirea_ikbo0619.promofinder.ui.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mirea_ikbo0619.promofinder.UserPreference
import edu.mirea_ikbo0619.promofinder.network.content
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.ErrorResponse
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.first
import edu.mirea_ikbo0619.promofinder.repository.CredentialsRepository
import org.koin.android.annotation.KoinViewModel
import retrofit2.HttpException


abstract class AuthViewModel : ViewModel() {
    val isSignInSelected = ObservableBoolean(true)
    val username = ObservableField("")
    val password = ObservableField("")
    val password2 = ObservableField("")
    val isPasswordVisible = ObservableField(false)
    val email = ObservableField("")
    var lastError: String? = null
    abstract val isAuthorized: LiveData<Boolean>
    abstract val isAnonymous: LiveData<Boolean>
    abstract val wasAuthorized: Boolean

    fun togglePasswordVisibility() {
        isPasswordVisible.set(!isPasswordVisible.get()!!)
    }
    abstract fun handleWasAuthorized(): Boolean
    abstract fun signInAnonymous(): Boolean
    abstract suspend fun signIn(): Boolean
    abstract suspend fun signUp(): Boolean
    abstract fun signOut()
}

@KoinViewModel
class AuthViewModelImpl(
    private val settings: UserPreference,
    private val credentials: CredentialsRepository
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

    override suspend fun signIn(): Boolean {
        try {
            lastError = null
            credentials.setCredentials(
                email.get() ?: "",
                password.get() ?: "",
            )
            authorize()
        } catch (e: HttpException) {
            if (e.code() == 400)
                lastError = e.content<ErrorResponse>()?.first
            return false
        } catch (e: Throwable) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override suspend fun signUp(): Boolean {
        try {
            lastError = null
            credentials.setCredentials(
                email.get()!!,
                password.get()!!,
                username.get()!!,
            )
            authorize()
        } catch (e: HttpException) {
            if (e.code() == 400)
                lastError = e.content<ErrorResponse>()?.first
            return false
        } catch (e: Throwable) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun signOut() {
        settings.wasAuthorized = false
        settings.token = ""
        isAuthorized.postValue(false)
    }
}