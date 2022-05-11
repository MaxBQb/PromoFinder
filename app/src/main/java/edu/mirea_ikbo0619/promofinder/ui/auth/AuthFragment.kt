package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.wada811.databinding.dataBinding
import edu.mirea_ikbo0619.promofinder.R
import edu.mirea_ikbo0619.promofinder.databinding.AuthFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.goBack
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AuthFragment : Fragment(R.layout.auth_fragment) {

    companion object {
        fun newInstance() = AuthFragment()
    }

    val viewModel: AuthViewModel by sharedViewModel()
    private val binding: AuthFragmentBinding by dataBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = SectionsPagerAdapter(this)
        binding.data = viewModel
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        viewModel.isSignInSelected.set(binding.tabs.selectedTabPosition == 0)
        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab)
                = viewModel.isSignInSelected.set(tab.position == 0)
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.back.setOnClickListener { goBack() }
        binding.next.setOnClickListener {
            lifecycleScope.launch {
                val isLoginAttempt = viewModel.isSignInSelected.get()
                val success = if (isLoginAttempt)
                    viewModel.signIn()
                else
                    viewModel.signUp()
                if (success) {
                    viewModel.password.set("")
                    viewModel.password2.set("")
                    goBack()
                } else
                    Snackbar.make(binding.root,
                        viewModel.lastError ?: getString(
                            if (isLoginAttempt)
                                R.string.login_error_message
                            else
                                R.string.signup_error_message
                        ),
                        BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
            }
        }
    }
}