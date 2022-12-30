package com.nhathm.jobhunt.ui.company

import androidx.lifecycle.viewModelScope
import com.nhathm.jobhunt.base.BaseViewModel
import com.nhathm.jobhunt.data.repository.AuthRepository
import com.nhathm.jobhunt.data.repository.CompanyRepository
import com.nhathm.jobhunt.data.request.CreateCompanyRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository
): BaseViewModel() {

    fun createCompany(request: CreateCompanyRequest) = viewModelScope.launch {
        repository.createCompany(request)
    }

}