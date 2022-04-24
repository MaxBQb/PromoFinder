package edu.mirea_ikbo0619.promofinder.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.wada811.databinding.dataBinding
import edu.mirea_ikbo0619.promofinder.MainActivity
import edu.mirea_ikbo0619.promofinder.R
import edu.mirea_ikbo0619.promofinder.databinding.HomeFragmentBinding
import edu.mirea_ikbo0619.promofinder.utils.autoCleaned
import edu.mirea_ikbo0619.promofinder.utils.hideKeyboard
import edu.mirea_ikbo0619.promofinder.utils.observe
import edu.mirea_ikbo0619.promofinder.utils.set
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by dataBinding()
    private var suggestionsAdapter: CompaniesListAdapter by autoCleaned()
    private var resultsAdapter: PromocodesListAdapter by autoCleaned()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = viewModel
        (requireActivity() as MainActivity).setHomeIndicator()

        suggestionsAdapter = CompaniesListAdapter()
        resultsAdapter = PromocodesListAdapter()
        binding.companiesList.adapter = suggestionsAdapter
        binding.promocodesList.adapter = resultsAdapter
        observe(viewModel.query) {
            lifecycleScope.launch {
                viewModel.getCompanies(it).collectLatest(suggestionsAdapter::submitData)
            }
        }
        observe(viewModel.selectedCompany) {
            it ?: return@observe
            lifecycleScope.launch {
                viewModel.getPromocodes(it).collectLatest(resultsAdapter::submitData)
            }
        }

        suggestionsAdapter.addLoadStateListener { loadState ->
            viewModel.hasSuggestions.set(
                !(loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached
                        && suggestionsAdapter.itemCount < 1)
            )
        }
        resultsAdapter.addLoadStateListener { loadState ->
            viewModel.isResultsVisible.set(
                !(loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached
                        && resultsAdapter.itemCount < 1)
            )
        }

        resultsAdapter.onItemButtonClickListener = { _, item, _ ->
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)!!
            val clip = ClipData.newPlainText(
                getString(
                    R.string.promocode_clipboard_description,
                    viewModel.selectedCompany.value?.name ?: "<*>"
                ), item.code
            )
            clipboard.setPrimaryClip(clip)
            Snackbar.make(binding.root, getString(R.string.copied_to_clipboard), LENGTH_SHORT)
                .show()
        }

        suggestionsAdapter.onItemClickListener = { _, item, _ ->
            viewModel.query.set(item.name)
            viewModel.selectedCompany.set(item)
            hideKeyboard()
            binding.root.clearFocus()
        }

        observe(viewModel.isSuggestionsVisible) {
            binding.companiesList.isVisible = it
            binding.noItemsFound.isVisible = it && !viewModel.hasSuggestions.value!!
        }

        observe(viewModel.hasSuggestions) {
            binding.noItemsFound.isVisible = !it && viewModel.isSuggestionsVisible.value!!
        }

        binding.search.setOnFocusChangeListener { view, isFocused ->
            viewModel.isSuggestionsVisible.set(isFocused)
        }

        observe(viewModel.isResultsVisible) {
            binding.welcomeLabel.isVisible = !it
            binding.promocodesList.isVisible = it
        }
    }
}