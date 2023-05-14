package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.data.request.*
import retrofit2.http.*

interface DoctorApi {

    @GET("doctors/get-top-doctors")
    suspend fun getTopDoctors(): List<Doctor>

    @GET("doctors/search")
    suspend fun searchDoctor(
        @Query("name") token: String,
        @Query("category") category: String
    ): List<Doctor>

    @POST("doctors/save-doctor")
    suspend fun saveDoctor(
        @Body doctor: Doctor,
    ): Doctor
}