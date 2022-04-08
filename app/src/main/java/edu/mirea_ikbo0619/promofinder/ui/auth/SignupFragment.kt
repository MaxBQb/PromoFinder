package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.mirea_ikbo0619.promofinder.databinding.SignupFragmentBinding
import lab.maxb.dark.Presentation.Extra.Delegates.autoCleaned


class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private var binding: SignupFragmentBinding by autoCleaned()
    private var viewModel: AuthViewModel by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SignupFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (parentFragment as AuthFragment).viewModel
        binding.data = viewModel
        val togglePassword = { _: View ->
            viewModel.togglePasswordVisibility()
            false
        }
        binding.password.setOnLongClickListener(togglePassword)
        binding.password2.setOnLongClickListener(togglePassword)
    }
}