package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.mirea_ikbo0619.promofinder.databinding.WelcomeFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.autoCleaned
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private val viewModel: AuthViewModel by sharedViewModel()
    private var binding: WelcomeFragmentBinding by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = WelcomeFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

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
        viewModel.isAuthorized.observe(viewLifecycleOwner) { authorized ->
            if (authorized) {
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                )
            } else if (viewModel.wasAuthorized)
                viewModel.handleWasAuthorized()
        }
    }
}