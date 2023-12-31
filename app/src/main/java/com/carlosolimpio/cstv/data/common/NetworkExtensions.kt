package com.carlosolimpio.cstv.data.common

import retrofit2.Response

suspend fun <T> Response<T>.handleResponse(
    onSuccess: suspend (T) -> Unit,
    onError: suspend (String) -> Unit
) {
    if (isSuccessful) {
        body()?.let {
            onSuccess(it)
        } ?: onError("Response not successful: Null api response")
    } else {
        onError("Response not successful: ${message()}")
    }
}
