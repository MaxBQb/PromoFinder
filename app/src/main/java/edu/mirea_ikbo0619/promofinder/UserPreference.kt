package edu.mirea_ikbo0619.promofinder

import android.content.Context
import androidx.preference.PreferenceManager
import edu.mirea_ikbo0619.promofinder.utils.property
import org.koin.core.annotation.Single

@Single
class UserPreference(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    var wasAuthorized by prefs.property(false)
    var token: String? by prefs.property("")
    val isAnonymous get() = token == ""
}
