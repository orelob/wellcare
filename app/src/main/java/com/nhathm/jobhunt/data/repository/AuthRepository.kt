package com.nhathm.jobhunt.data.repository

import android.util.Log
import com.nhathm.jobhunt.base.BaseRepository
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.data.api.AuthApi
import com.nhathm.jobhunt.data.request.ChangePasswordRequest
import com.nhathm.jobhunt.data.request.LoginRequest
import com.nhathm.jobhunt.data.request.SaveFcmTokenRequest
import com.nhathm.jobhunt.data.request.SignUpRequest
import com.nhathm.jobhunt.data.response.LoginResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val localDataStore: LocalDataStore
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        authApi.login(LoginRequest(email, password))
    }

    suspend fun signUp(
        fullName: String,
        email: String,
        password: String
    ) = safeApiCall {
        authApi.signup(SignUpRequest(fullName, email, password))
    }

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) = safeApiCall {
        authApi.changePassword(ChangePasswordRequest(currentPassword, newPassword, confirmPassword))
    }

    suspend fun saveAccessToken(accessToken: String) {
        Log.i("TOKENAUTH", accessToken)
        localDataStore.saveAccessToken(accessToken)
    }

    suspend fun saveFcmTokenForUser(userId: String, fcmToken: String) = safeApiCall {
        authApi.saveFcmTokenRequest(SaveFcmTokenRequest(userId, fcmToken))
    }

    suspend fun logout() {
        localDataStore.clear()
    }
}