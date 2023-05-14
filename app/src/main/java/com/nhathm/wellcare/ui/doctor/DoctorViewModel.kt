package com.nhathm.wellcare.ui.doctor

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
class DoctorViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : BaseViewModel() {

    private val _doctorList: MutableLiveData<Resource<List<Doctor>>> =
        MutableLiveData()
    val doctorList: LiveData<Resource<List<Doctor>>>
        get() = _doctorList

    fun searchDoctor(name: String, category: String) = viewModelScope.launch {
        _doctorList.value = Resource.Loading
        delay(1000)
        _doctorList.value = doctorRepository.searchDoctor(name, category)
    }


    private val _saveDoctor: MutableLiveData<Resource<Doctor>> =
        MutableLiveData()
    val saveDoctor: LiveData<Resource<Doctor>>
        get() = _saveDoctor

    fun saveDoctor(doctor: Doctor) = viewModelScope.launch {
        _saveDoctor.value = Resource.Loading
        _saveDoctor.value = doctorRepository.saveDoctor(doctor)
    }
}