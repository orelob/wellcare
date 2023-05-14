package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.request.*
import retrofit2.http.*

interface AppointmentApi {

    @GET("appointments/get-upcoming-appointments")
    suspend fun getUpcomingAppointments(): List<Appointment>
}