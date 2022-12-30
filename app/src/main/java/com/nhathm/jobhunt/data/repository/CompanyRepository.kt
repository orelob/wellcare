package com.nhathm.jobhunt.data.repository

import com.nhathm.jobhunt.base.BaseRepository
import com.nhathm.jobhunt.data.api.CompanyApi
import com.nhathm.jobhunt.data.request.CreateCompanyRequest
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val companyApi: CompanyApi
) : BaseRepository() {

    suspend fun getCompanies() = safeApiCall {
//        companyApi.getCompanies()
    }

    suspend fun createCompany(request: CreateCompanyRequest) = safeApiCall {
        companyApi.createCompany(request)
    }
}
