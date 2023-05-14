package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.data.JwtResponse
import com.nhathm.wellcare.data.User
import com.nhathm.wellcare.data.request.*
import com.nhathm.wellcare.data.response.SignInResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/sign-up")
    suspend fun signUp(@Body request: SignUpRequest): String

    @POST("auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse

    @GET("auth/current-user")
    suspend fun getCurrentUser(): User

    @POST("auth/save-fcm-token")
    suspend fun saveFirebaseToken(
        @Query("fcm_token") token: String
    ): String





    @POST("auth/logout")
    suspend fun logout(): ResponseBody
//
//
//    @POST("auth/change-password")
//    suspend fun changePassword(@Body request: ChangePasswordRequest): BaseApiResponse
}