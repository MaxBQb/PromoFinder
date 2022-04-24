package edu.mirea_ikbo0619.promofinder.network.promocode_aggregator.model

class Response<T : Any>(
    val returnedItemsLength: Int,
    val length: Int,
    val items: List<T>
)