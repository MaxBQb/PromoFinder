package edu.mirea_ikbo0619.promofinder.network

import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.io.IOException

inline fun <reified T> HttpException.content() = response()?.errorBody()?.string()?.let {
    try {
        GsonBuilder().create().fromJson(it, T::class.java)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}