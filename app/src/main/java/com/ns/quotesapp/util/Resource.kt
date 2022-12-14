package com.ns.quotesapp.util

sealed class Resource<T>(
    val data: List<T>? = null,
    val message: String? = null
) {
    class Success<T>(data: List<T>) : Resource<T>(data)
    class Error<T>(message: String, data: List<T>? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}