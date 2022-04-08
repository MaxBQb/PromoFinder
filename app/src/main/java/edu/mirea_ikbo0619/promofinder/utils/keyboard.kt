package edu.mirea_ikbo0619.promofinder.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.hideKeyboard(): Boolean {
    val inputManager = (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager) ?: return false
    val token = currentFocus?.windowToken ?: return false
    return inputManager.hideSoftInputFromWindow(token, 0)
}

fun Fragment.hideKeyboard() = requireActivity().hideKeyboard()
