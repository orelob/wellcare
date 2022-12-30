package com.nhathm.jobhunt.data.api

import com.nhathm.jobhunt.base.BaseApiResponse
import com.nhathm.jobhunt.data.request.ChangePasswordRequest
import com.nhathm.jobhunt.data.request.LoginRequest
import com.nhathm.jobhunt.data.request.SaveFcmTokenRequest
import com.nhathm.jobhunt.data.request.SignUpRequest
import com.nhathm.jobhunt.data.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/sign-up")
    suspend fun signup(@Body request: SignUpRequest): BaseApiResponse

    @POST("auth/logout")
    suspend fun logout(): BaseApiResponse

    @POST("auth/save-fcm-token")
    suspend fun saveFcmTokenRequest(@Body request: SaveFcmTokenRequest): BaseApiResponse

    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): BaseApiResponse
}