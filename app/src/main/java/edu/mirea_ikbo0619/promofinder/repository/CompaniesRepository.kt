package edu.mirea_ikbo0619.promofinder.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.PromocodeAggregatorService
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.pagination.CompaniesPagingSource
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class CompaniesRepository(
    private val service: PromocodeAggregatorService
) : KoinComponent {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
        private val pagingConfig = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        )
    }

    fun getCompanies(query: String) = Pager(
        config = pagingConfig,
        pagingSourceFactory = { CompaniesPagingSource(service, query) }
    )

}