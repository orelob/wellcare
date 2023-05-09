package com.nhathm.wellcare.data.repository

import android.util.Log
import com.nhathm.wellcare.base.BaseRepository
import com.nhathm.wellcare.data.LocalDataStore
import com.nhathm.wellcare.data.SharedPreferencesManager
import com.nhathm.wellcare.data.api.AuthApi
import com.nhathm.wellcare.data.request.*
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val localDataStore: LocalDataStore
) : BaseRepository() {

    suspend fun signUp(
        payload: SignUpRequest
    ) = safeApiCall {
        authApi.signUp(payload)
    }

    suspend fun signIn(
        email: String,
        password: String
    ) = safeApiCall {
        authApi.signIn(SignInRequest(email, password))
    }

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) = safeApiCall {
        authApi.changePassword(ChangePasswordRequest(currentPassword, newPassword, confirmPassword))
    }

    suspend fun saveAccessToken(accessToken: String) {
        localDataStore.saveAccessToken(accessToken)
    }

    suspend fun saveFcmTokenForUser(fcmToken: String) = safeApiCall {
        authApi.saveFirebaseToken(fcmToken)
    }

    suspend fun logout() = safeApiCall {
        SharedPreferencesManager.setAuthToken(null)
        SharedPreferencesManager.setRole(null)
    }
}