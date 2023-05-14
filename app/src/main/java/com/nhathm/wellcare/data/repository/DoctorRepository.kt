package com.nhathm.wellcare.data.repository

import com.nhathm.wellcare.base.BaseRepository
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.data.api.AppointmentApi
import com.nhathm.wellcare.data.api.DoctorApi
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    private val doctorApi: DoctorApi
) : BaseRepository() {

    suspend fun getTopDoctors() = safeApiCall {
        doctorApi.getTopDoctors()
    }

    suspend fun searchDoctor(name: String, category: String) = safeApiCall {
        doctorApi.searchDoctor(name, category)
    }

    suspend fun saveDoctor(doctor: Doctor) = safeApiCall {
        doctorApi.saveDoctor(doctor)
    }
}