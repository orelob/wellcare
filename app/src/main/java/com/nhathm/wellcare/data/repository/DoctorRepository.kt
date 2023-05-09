package com.nhathm.wellcare.data.repository

import com.nhathm.wellcare.base.BaseRepository
import com.nhathm.wellcare.data.api.AppointmentApi
import com.nhathm.wellcare.data.api.DoctorApi
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    private val doctorApi: DoctorApi
) : BaseRepository() {

    suspend fun getTopDoctors() = safeApiCall {
        doctorApi.getTopDoctors()
    }
}