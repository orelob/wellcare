package com.nhathm.wellcare.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}