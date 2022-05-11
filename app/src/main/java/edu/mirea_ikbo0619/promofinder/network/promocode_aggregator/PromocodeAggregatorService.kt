package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator

import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.LoginRequest
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model.SignupRequest
import okhttp3.OkHttpClient
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.get
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Single
class PromocodeAggregatorService {
    private val api = build()

    suspend fun getCompanies(query: String, page: Int, portion: Int = 20)
        = with(fromPage(page, portion)) {
        api.getCompanies(query, get(0), get(1))
    }

    suspend fun getPromocodes(companyId: String, page: Int, portion: Int = 20)
        = with(fromPage(page, portion)) {
        api.getPromocodes(companyId, get(0), get(1))
    }

    suspend fun login(email: String, password: String)
        = api.login(LoginRequest(email, password)).token

    suspend fun signup(email: String, password: String, name: String): String {
        api.signup(SignupRequest(email, password, password, name))
        return api.login(LoginRequest(email, password)).token
    }

    private fun fromPage(page: Int, portion: Int = 20) = arrayOf(page * portion, portion)
}

private const val PROMOCODE_AGGREGATOR_SERVICE_URL = "http://mc.icomm.pro:9080"

private fun build(): PromocodeAggregatorAPI = Retrofit.Builder()
    .baseUrl(PROMOCODE_AGGREGATOR_SERVICE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttpClient())
    .build()
    .create(PromocodeAggregatorAPI::class.java)

private fun okhttpClient() = OkHttpClient.Builder()
    .addInterceptor(get(AuthInterceptor::class.java))
    .build()
