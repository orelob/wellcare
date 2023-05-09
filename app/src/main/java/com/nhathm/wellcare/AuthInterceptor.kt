package com.nhathm.wellcare

import android.util.Log
import com.nhathm.wellcare.data.SharedPreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(

): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            SharedPreferencesManager.getAuthToken()
        }
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        Log.i("Authorization: ", "Bearer $token")
        return chain.proceed(request.build())
    }
}