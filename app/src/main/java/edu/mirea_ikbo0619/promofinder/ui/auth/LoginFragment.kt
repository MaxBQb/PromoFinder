package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import edu.mirea_ikbo0619.promofinder.databinding.LoginFragmentBinding
import lab.maxb.dark.Presentation.Extra.Delegates.autoCleaned


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var binding: LoginFragmentBinding by autoCleaned()
    private var viewModel: AuthViewModel by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LoginFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (parentFragment as AuthFragment).viewModel
        binding.data = viewModel
        binding.password.setOnLongClickListener {
            viewModel.togglePasswordVisibility()
            false
        }
    }
}