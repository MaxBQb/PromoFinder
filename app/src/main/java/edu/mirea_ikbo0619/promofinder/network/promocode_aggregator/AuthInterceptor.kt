package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator

import edu.mirea_ikbo0619.promofinder.UserPreference
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.annotation.Single

@Single
class AuthInterceptor(
    private val settings: UserPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        with(chain.request().newBuilder()) {
            addHeader("Authorization", "Bearer ${settings.token}")
            chain.proceed(build())
        }
}
