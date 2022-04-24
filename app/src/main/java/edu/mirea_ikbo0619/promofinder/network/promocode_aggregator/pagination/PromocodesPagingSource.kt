package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import edu.mirea_ikbo0619.promofinder.model.Promocode
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.PromocodeAggregatorService
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.fromPage
import retrofit2.HttpException
import java.io.IOException

class PromocodesPagingSource(
    private val service: PromocodeAggregatorService,
    private val companyId: String
) : PagingSource<Int, Promocode>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Promocode> {
        val position = params.key ?: 0
        return try {
            val response = with(fromPage(position)) {
                service.getPromocodes(companyId, get(0), get(1))
            }
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

    override fun getRefreshKey(state: PagingState<Int, Promocode>) = state.anchorPosition
}