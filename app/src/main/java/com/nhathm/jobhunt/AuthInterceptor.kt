package com.nhathm.jobhunt

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenService: TokenService,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenService.getToken().first()
        }
        val request = chain.request().newBuilder()
        request.addHeader("access_token", "$token")
        Log.i("AuthInterceptor", "token: $token")
        return chain.proceed(request.build())
    }
}