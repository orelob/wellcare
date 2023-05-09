package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.base.BaseApiResponse
import com.nhathm.wellcare.base.BaseResponse
import com.nhathm.wellcare.data.request.*
import com.nhathm.wellcare.data.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/sign-up")
    suspend fun signUp(@Body request: SignUpRequest): BaseApiResponse

    @POST("auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): BaseApiResponse

    @POST("auth/save-fcm-token")
    suspend fun saveFirebaseToken(
        @Query("fcm_token") token: String
    ): BaseApiResponse





    @POST("auth/logout")
    suspend fun logout(): BaseApiResponse


    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): BaseApiResponse
}