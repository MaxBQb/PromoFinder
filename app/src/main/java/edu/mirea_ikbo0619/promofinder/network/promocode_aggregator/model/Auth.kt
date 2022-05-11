package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model

data class LoginRequest(
    val email: String,
    val password: String,
)

data class SignupRequest(
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val username: String,
)

data class TokenResponse(
    val token: String,
)

data class ErrorResponse(
    val errors: Errors?,
) {
    data class Errors(
        val Email: List<String>?,
        val Password: List<String>?
    )
}

val ErrorResponse.first get() = errors?.let {
    it.Email?.firstOrNull() ?: it.Password?.firstOrNull()
}