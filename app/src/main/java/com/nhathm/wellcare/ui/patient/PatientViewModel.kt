package com.nhathm.wellcare.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.wellcare.base.BaseApiResponse
import com.nhathm.wellcare.base.BaseViewModel
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.repository.AppointmentRepository
import com.nhathm.wellcare.data.repository.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val doctorRepository: DoctorRepository
) : BaseViewModel() {

    private val _upcomingAppointmentList: MutableLiveData<Resource<BaseApiResponse>> =
        MutableLiveData()
    val upcomingAppointmentList: LiveData<Resource<BaseApiResponse>>
        get() = _upcomingAppointmentList

    fun getUpcomingAppointments() = viewModelScope.launch {
        _upcomingAppointmentList.value = Resource.Loading
        _upcomingAppointmentList.value = appointmentRepository.getUpcomingAppointments()
    }


    private val _topDoctorList: MutableLiveData<Resource<BaseApiResponse>> =
        MutableLiveData()
    val topDoctorList: LiveData<Resource<BaseApiResponse>>
        get() = _topDoctorList

    fun getTopDoctors() = viewModelScope.launch {
        _topDoctorList.value = Resource.Loading
        _topDoctorList.value = doctorRepository.getTopDoctors()
    }

    init {
        getUpcomingAppointments()
        getTopDoctors()
    }
}