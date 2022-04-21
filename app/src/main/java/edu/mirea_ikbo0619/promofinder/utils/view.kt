package edu.mirea_ikbo0619.promofinder.utils

import android.view.View
import android.view.View.*
import androidx.core.view.isVisible

fun View.setVisibility(isVisible: Boolean, preserveSpace: Boolean = false) {
    visibility = when {
        isVisible -> VISIBLE
        !isVisible and preserveSpace -> INVISIBLE
        else -> GONE
    }
}

fun View.hide(preserveSpace: Boolean = false)
    = setVisibility(false, preserveSpace)

fun View.show() = setVisibility(true)

fun View.toggleVisibility(preserveSpace: Boolean = false)
    = setVisibility(!isVisible, preserveSpace)
