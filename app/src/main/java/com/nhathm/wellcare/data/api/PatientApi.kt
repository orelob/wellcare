package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.base.BaseApiResponse
import com.nhathm.wellcare.base.BaseResponse
import com.nhathm.wellcare.data.request.*
import com.nhathm.wellcare.data.response.SignInResponse
import retrofit2.http.*

interface PatientApi {

    @GET("appointments/get-upcoming-appointments")
    suspend fun getUpcomingAppointmentList(): BaseApiResponse
}