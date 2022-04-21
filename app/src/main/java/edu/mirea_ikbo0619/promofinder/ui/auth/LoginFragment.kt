package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.mirea_ikbo0619.promofinder.databinding.LoginFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.autoCleaned
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var binding: LoginFragmentBinding by autoCleaned()
    private val viewModel: AuthViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LoginFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.password.setOnLongClickListener {
            viewModel.togglePasswordVisibility()
            false
        }
    }
}