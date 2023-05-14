package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.request.*
import retrofit2.http.*

interface PatientApi {

    @GET("appointments/get-upcoming-appointments")
    suspend fun getUpcomingAppointmentList(): List<Appointment>
}