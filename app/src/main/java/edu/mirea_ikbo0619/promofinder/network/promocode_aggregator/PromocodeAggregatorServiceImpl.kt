package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator

import okhttp3.OkHttpClient
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.get
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Single
class PromocodeAggregatorServiceImpl : PromocodeAggregatorService by build()

private const val PROMOCODE_AGGREGATOR_SERVICE_URL = "http://mc.icomm.pro:9080"

private fun build(): PromocodeAggregatorService = Retrofit.Builder()
    .baseUrl(PROMOCODE_AGGREGATOR_SERVICE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttpClient())
    .build()
    .create(PromocodeAggregatorService::class.java)

private fun okhttpClient() = OkHttpClient.Builder()
    .addInterceptor(get(AuthInterceptor::class.java))
    .build()

fun fromPage(page: Int, portion: Int = 20) = arrayOf(page * portion, portion)