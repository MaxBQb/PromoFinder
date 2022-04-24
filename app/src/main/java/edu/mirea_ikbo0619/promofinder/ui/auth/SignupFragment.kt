package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wada811.databinding.dataBinding
import edu.mirea_ikbo0619.promofinder.R
import edu.mirea_ikbo0619.promofinder.databinding.SignupFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignupFragment : Fragment(R.layout.signup_fragment) {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private val binding: SignupFragmentBinding by dataBinding()
    private val viewModel: AuthViewModel by sharedViewModel()

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