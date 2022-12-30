package com.nhathm.jobhunt.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.jobhunt.base.BaseViewModel
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.data.model.User
import com.nhathm.jobhunt.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): BaseViewModel() {

    @Inject lateinit var localDataStore: LocalDataStore

    private val _userAuth: MutableLiveData<Resource<User>> = MutableLiveData()
    val userAuth: LiveData<Resource<User>>
        get() = _userAuth

    fun getUserAuth() = viewModelScope.launch {
        _userAuth.value = Resource.Loading
        _userAuth.value = repository.getUserAuth()
    }
}