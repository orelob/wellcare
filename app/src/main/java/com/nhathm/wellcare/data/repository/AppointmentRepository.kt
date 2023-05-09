package com.nhathm.wellcare.data.repository

import com.nhathm.wellcare.base.BaseRepository
import com.nhathm.wellcare.data.api.AppointmentApi
import javax.inject.Inject

class AppointmentRepository @Inject constructor(
    private val appointmentApi: AppointmentApi
) : BaseRepository() {

    suspend fun getUpcomingAppointments() = safeApiCall {
        appointmentApi.getUpcomingAppointments()
    }
}