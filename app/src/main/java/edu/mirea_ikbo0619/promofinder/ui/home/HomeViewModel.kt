package edu.mirea_ikbo0619.promofinder.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import edu.mirea_ikbo0619.promofinder.model.Company
import edu.mirea_ikbo0619.promofinder.model.Promocode
import edu.mirea_ikbo0619.promofinder.repository.CompaniesRepository
import edu.mirea_ikbo0619.promofinder.repository.PromocodesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val companiesRepository: CompaniesRepository,
    private val promocodesRepository: PromocodesRepository,
) : ViewModel() {
    val query = MutableLiveData("")
    val selectedCompany = MutableLiveData<Company?>()

    val isSuggestionsVisible = MutableLiveData(false)
    val isResultsVisible = MutableLiveData(false)
    val hasSuggestions = MutableLiveData(false)

    fun getCompanies(query: String): Flow<PagingData<Company>> =
        companiesRepository.getCompanies(query).flow

    fun getPromocodes(company: Company): Flow<PagingData<Promocode>> =
        promocodesRepository.getPromocodes(company.id).flow.cachedIn(viewModelScope)
}