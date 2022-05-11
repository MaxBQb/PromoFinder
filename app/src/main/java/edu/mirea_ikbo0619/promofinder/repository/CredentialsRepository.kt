package edu.mirea_ikbo0619.promofinder.repository

import edu.mirea_ikbo0619.promofinder.UserPreference
import edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.PromocodeAggregatorService
import org.koin.core.annotation.Single

@Single
class CredentialsRepository(
    private val service: PromocodeAggregatorService,
    private val settings: UserPreference
) {
   suspend fun setCredentials(login: String, password: String, name: String? = null) {
       settings.token = name?.let {
           service.signup(login, password, it)
       } ?: service.login(login, password)
   }
}