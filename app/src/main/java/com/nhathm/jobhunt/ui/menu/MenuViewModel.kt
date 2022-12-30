package com.nhathm.jobhunt.ui.menu

import androidx.lifecycle.viewModelScope
import com.nhathm.jobhunt.base.BaseViewModel
import com.nhathm.jobhunt.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel() {

     fun logout() = viewModelScope.launch {
          repository.logout()
     }

}