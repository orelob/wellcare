package com.nhathm.jobhunt.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.jobhunt.base.BaseApiResponse
import com.nhathm.jobhunt.base.BaseViewModel
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.data.model.User
import com.nhathm.jobhunt.data.repository.AuthRepository
import com.nhathm.jobhunt.data.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel() {

    @Inject lateinit var localDataStore: LocalDataStore

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _signupResponse: MutableLiveData<Resource<BaseApiResponse>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<BaseApiResponse>>
        get() = _signupResponse

    private val _changePasswordResponse: MutableLiveData<Resource<BaseApiResponse>> = MutableLiveData()
    val changePasswordResponse: LiveData<Resource<BaseApiResponse>>
        get() = _changePasswordResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    fun signup(
        fullName: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _signupResponse.value = Resource.Loading
        _signupResponse.value = repository.signUp(fullName, email, password)
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        _changePasswordResponse.value = Resource.Loading
        _changePasswordResponse.value = repository.changePassword(currentPassword, newPassword, confirmPassword)
    }

    suspend fun saveAccessToken(accessToken: String) {
        repository.saveAccessToken(accessToken)
    }

    fun getAccessToken(): String {
        return localDataStore.accessToken.toString()
    }

    suspend fun saveFcmTokenForUser(userId: String, fcmToken: String) {
        repository.saveFcmTokenForUser(userId, fcmToken)
    }

    fun logout() = viewModelScope.launch {
        repository.logout()
    }
}