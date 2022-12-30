package com.nhathm.jobhunt.data.api

import com.nhathm.jobhunt.data.model.Company
import com.nhathm.jobhunt.data.request.CreateCompanyRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CompanyApi {

    @POST("companies/create")
    suspend fun createCompany(@Body company: CreateCompanyRequest): Company
}