package edu.mirea_ikbo0619.promofinder.ui.auth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.mirea_ikbo0619.promofinder.R

val TAB_TITLES = arrayOf(
        R.string.tab_signin_label,
        R.string.tab_signup_label,
)

class SectionsPagerAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment) {
    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int)
        = if (position == 0)
            LoginFragment.newInstance()
          else
            SignupFragment.newInstance()
}