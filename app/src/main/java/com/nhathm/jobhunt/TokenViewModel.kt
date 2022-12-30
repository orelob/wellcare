package com.nhathm.jobhunt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenService: TokenService,
): ViewModel() {

    val token = MutableLiveData<String?>()
    private var mJob: Job? = null

    init {
        mJob = viewModelScope.launch {
            tokenService.getToken().collect {
                token.value = it
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            tokenService.saveToken(token)
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            tokenService.deleteToken()
        }
    }

    private fun removeObservables() {
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
        mJob = null
    }

    override fun onCleared() {
        super.onCleared()
        removeObservables()
    }
}