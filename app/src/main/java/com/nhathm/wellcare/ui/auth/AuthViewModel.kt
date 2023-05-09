package com.nhathm.wellcare.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.wellcare.base.BaseApiResponse
import com.nhathm.wellcare.base.BaseViewModel
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.LocalDataStore
import com.nhathm.wellcare.data.repository.AuthRepository
import com.nhathm.wellcare.data.request.SignUpRequest
import com.nhathm.wellcare.data.response.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel() {

    /////////////////////////// sign-up
    private val _signUpResponse: MutableLiveData<Resource<BaseApiResponse>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<BaseApiResponse>>
        get() = _signUpResponse

    fun signup(
        payload: SignUpRequest
    ) = viewModelScope.launch {
        _signUpResponse.value = Resource.Loading
        _signUpResponse.value = repository.signUp(payload)
    }

    /////////////////////////// sign-in
    private val _signInResponse: MutableLiveData<Resource<BaseApiResponse>> = MutableLiveData()
    val signInResponse: LiveData<Resource<BaseApiResponse>>
        get() = _signInResponse

    fun signIn(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _signInResponse.value = Resource.Loading
        _signInResponse.value = repository.signIn(email, password)
    }

    fun logout() = viewModelScope.launch {
        repository.logout()
    }
}