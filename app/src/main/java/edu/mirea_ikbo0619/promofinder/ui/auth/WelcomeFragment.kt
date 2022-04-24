package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wada811.databinding.dataBinding
import edu.mirea_ikbo0619.promofinder.R
import edu.mirea_ikbo0619.promofinder.databinding.WelcomeFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.observe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class WelcomeFragment : Fragment(R.layout.welcome_fragment) {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private val viewModel: AuthViewModel by sharedViewModel()
    private val binding: WelcomeFragmentBinding by dataBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextSigned.setOnClickListener {
            findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToAuthFragment()
            )
        }
        binding.nextUnauthorized.setOnClickListener {
            viewModel.signInAnonymous()
        }
        observe(viewModel.isAuthorized) { authorized ->
            binding.buttonsContainer.isVisible = when {
                authorized -> {
                    findNavController().navigate(
                        WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                    )
                    false
                }
                viewModel.wasAuthorized -> !viewModel.handleWasAuthorized()
                else -> true
            }
        }
    }
}