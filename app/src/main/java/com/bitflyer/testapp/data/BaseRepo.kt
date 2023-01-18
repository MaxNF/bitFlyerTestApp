package com.bitflyer.testapp.data

import com.bitflyer.testapp.exception.EmptyResultException
import com.bitflyer.testapp.exception.NetworkException
import kotlinx.coroutines.delay
import retrofit2.Response

open class BaseRepo {
    companion object {
        const val MAX_TRIES = 3
    }

    protected suspend fun <T> performWithRetry(action: suspend () -> Response<T>): T {
        var counter = 0
        var response: Response<T>? = null
        while (counter < MAX_TRIES) {
            response = action()
            if (response.code() == 200) {
                return response.body() ?: throw EmptyResultException()
            }
            else counter++
            delay(1000)
        }
        throw NetworkException(response?.code() ?: 0, response?.message() ?: "")
    }
}