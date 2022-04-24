package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator

import edu.mirea_ikbo0619.promofinder.model.Company
import edu.mirea_ikbo0619.promofinder.model.Promocode
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val path = "api"

interface PromocodeAggregatorService {

    @GET("$path/companies")
    suspend fun getCompanies(
        @Query("query") query: String,
        @Query("skip") skip: Int,
        @Query("take") take: Int
    ): Response<Company>

    @GET("$path/promo-codes")
    suspend fun getPromocodes(
        @Query("companyId") companyId: String,
        @Query("skip") skip: Int,
        @Query("take") take: Int
    ): Response<Promocode>
}
