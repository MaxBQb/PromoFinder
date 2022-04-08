package edu.mirea_ikbo0619.promofinder.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.goBack() {
    hideKeyboard()
    findNavController().popBackStack()
}