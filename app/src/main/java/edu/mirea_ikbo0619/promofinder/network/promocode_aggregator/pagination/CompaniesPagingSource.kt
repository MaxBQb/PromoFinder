package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import edu.mirea_ikbo0619.promofinder.model.Company
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.PromocodeAggregatorService
import retrofit2.HttpException
import java.io.IOException

class CompaniesPagingSource(
    private val service: PromocodeAggregatorService,
    private val query: String
) : PagingSource<Int, Company>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Company> {
        val position = params.key ?: 0
        return try {
            val response = service.getCompanies(query, position)
            val items = response.items
            LoadResult.Page(
                data = items,
                prevKey = if (position == 0) null else position,
                nextKey = if (items.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Company>) = state.anchorPosition
}