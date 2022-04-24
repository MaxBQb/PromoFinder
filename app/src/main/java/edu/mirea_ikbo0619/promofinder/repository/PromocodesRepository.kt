package edu.mirea_ikbo0619.promofinder.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.PromocodeAggregatorService
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.pagination.PromocodesPagingSource
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class PromocodesRepository(
    private val service: PromocodeAggregatorService
) : KoinComponent {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
        private val pagingConfig = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = true
        )
    }

    fun getPromocodes(companyId: String) = Pager(
        config = pagingConfig,
        pagingSourceFactory = { PromocodesPagingSource(service, companyId) }
    )
}