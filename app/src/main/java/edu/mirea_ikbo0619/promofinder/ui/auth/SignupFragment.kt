package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.mirea_ikbo0619.promofinder.databinding.SignupFragmentBinding
import lab.maxb.dark.Presentation.Extra.Delegates.autoCleaned
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private var binding: SignupFragmentBinding by autoCleaned()
    private val viewModel: AuthViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SignupFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = viewModel
        val togglePassword = { _: View ->
            viewModel.togglePasswordVisibility()
            false
        }
        binding.password.setOnLongClickListener(togglePassword)
        binding.password2.setOnLongClickListener(togglePassword)
    }
}