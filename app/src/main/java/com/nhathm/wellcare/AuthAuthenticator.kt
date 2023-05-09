package com.nhathm.wellcare

import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class AuthAuthenticator @Inject constructor(
    private val tokenService: TokenService,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenService.getToken().first()
        }
        return runBlocking {
            token?.let {
                response.request.newBuilder()
                    .header("access_token", token)
                    .build()
            }
        }
    }

}