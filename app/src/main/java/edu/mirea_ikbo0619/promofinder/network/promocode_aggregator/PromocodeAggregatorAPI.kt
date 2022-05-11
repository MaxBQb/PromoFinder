package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator

import edu.mirea_ikbo0619.promofinder.model.Company
import edu.mirea_ikbo0619.promofinder.model.Promocode
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.LoginRequest
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.Response
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.SignupRequest
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val path = "api"
const val users = "$path/users"

interface PromocodeAggregatorAPI {

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

    @POST("$users/token")
    suspend fun login(@Body credentials: LoginRequest): TokenResponse

    @POST("$users/register")
    suspend fun signup(@Body credentials: SignupRequest)
}
