package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wada811.databinding.dataBinding
import edu.mirea_ikbo0619.promofinder.R
import edu.mirea_ikbo0619.promofinder.databinding.LoginFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val binding: LoginFragmentBinding by dataBinding()
    private val viewModel: AuthViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = viewModel
        binding.password.setOnLongClickListener {
            viewModel.togglePasswordVisibility()
            false
        }
    }
}