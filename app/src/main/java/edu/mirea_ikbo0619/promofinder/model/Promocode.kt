package edu.mirea_ikbo0619.promofinder.model

data class Promocode(
    val id: String,
    val code: String,
    val expiration: String,
    val description: String,
    val sourceUrl: String,
)
