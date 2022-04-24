package edu.mirea_ikbo0619.promofinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mirea_ikbo0619.promofinder.utils.set
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel : ViewModel() {
    private val _isHomePage = MutableLiveData(false)
    val isHomePage: LiveData<Boolean> = _isHomePage

    fun setHomeIndicator() = _isHomePage.set(true)
}