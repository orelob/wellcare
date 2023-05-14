package com.nhathm.wellcare.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.wellcare.base.BaseViewModel
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.data.repository.AppointmentRepository
import com.nhathm.wellcare.data.repository.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val doctorRepository: DoctorRepository
) : BaseViewModel() {

    private val _upcomingAppointmentList: MutableLiveData<Resource<List<Appointment>>> =
        MutableLiveData()
    val upcomingAppointmentList: LiveData<Resource<List<Appointment>>>
        get() = _upcomingAppointmentList

    fun getUpcomingAppointments() = viewModelScope.launch {
        _upcomingAppointmentList.value = Resource.Loading
        _upcomingAppointmentList.value = appointmentRepository.getUpcomingAppointments()
    }

    private val _topDoctorList: MutableLiveData<Resource<List<Doctor>>> =
        MutableLiveData()
    val topDoctorList: LiveData<Resource<List<Doctor>>>
        get() = _topDoctorList

    fun getTopDoctors() = viewModelScope.launch {
        _topDoctorList.value = Resource.Loading
        delay(2000)
        _topDoctorList.value = doctorRepository.getTopDoctors()
    }

    init {
        getUpcomingAppointments()
        getTopDoctors()
    }
}