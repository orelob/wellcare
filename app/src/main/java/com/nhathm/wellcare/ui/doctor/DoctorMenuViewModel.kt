package com.nhathm.wellcare.ui.doctor

import androidx.lifecycle.viewModelScope
import com.nhathm.wellcare.base.BaseViewModel
import com.nhathm.wellcare.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorMenuViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel() {

     fun logout() = viewModelScope.launch {
          repository.logout()
     }
}