package edu.mirea_ikbo0619.promofinder.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import edu.mirea_ikbo0619.promofinder.databinding.AuthFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.goBack
import edu.mirea_ikbo0619.promofinder.utils.autoCleaned
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    val viewModel: AuthViewModel by sharedViewModel()
    private var binding: AuthFragmentBinding by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AuthFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = SectionsPagerAdapter(this)
        binding.data = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab)
                = viewModel.isSignInSelected.set(tab.position == 0)
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.back.setOnClickListener { goBack() }
        binding.next.setOnClickListener {
            val success = if (viewModel.isSignInSelected.get())
                viewModel.signIn()
            else
                viewModel.signUp()
            if (success)
                goBack()
        }
    }
}