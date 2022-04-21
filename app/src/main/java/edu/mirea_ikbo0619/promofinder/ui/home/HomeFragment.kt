package edu.mirea_ikbo0619.promofinder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.mirea_ikbo0619.promofinder.MainActivity
import edu.mirea_ikbo0619.promofinder.databinding.HomeFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.autoCleaned
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModel()
    private var binding: HomeFragmentBinding by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = HomeFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        (requireActivity() as MainActivity).setHomeIndicator()
    }
}