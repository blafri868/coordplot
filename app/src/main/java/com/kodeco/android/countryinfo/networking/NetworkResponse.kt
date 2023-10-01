package com.kodeco.android.countryinfo.networking

sealed class NetworkResponse<T: Any> {
    data class HTTPSuccess<T: Any>(val data: T) : NetworkResponse<T>()
    data class HTTPError<T : Any>(val code: Int) : NetworkResponse<T>()
    data class Exception<T : Any>(val error: Throwable) : NetworkResponse<T>()
}
