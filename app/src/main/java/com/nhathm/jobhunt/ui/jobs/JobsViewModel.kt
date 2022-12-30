package com.nhathm.jobhunt.ui.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.jobhunt.base.BaseViewModel
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.UserRepository
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.data.repository.JobRepository
import com.nhathm.jobhunt.data.request.JobFilter
import com.nhathm.jobhunt.data.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.logging.Filter
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository
) :
    BaseViewModel() {

    private val _jobList: MutableLiveData<Resource<List<JobDto>>> = MutableLiveData()
    val jobList: LiveData<Resource<List<JobDto>>>
        get() = _jobList

//    fun getJobs() = viewModelScope.launch {
//        _jobList.value = Resource.Loading
//        _jobList.value = jobRepository.getJobs()
//    }

    private val _appliedJobList: MutableLiveData<Resource<List<JobDto>>> = MutableLiveData()
    val appliedJobList: LiveData<Resource<List<JobDto>>>
        get() = _appliedJobList

    fun getAppliedJobs() = viewModelScope.launch {
        _appliedJobList.value = Resource.Loading
        _appliedJobList.value = jobRepository.getAppliedJobs()
    }

    private val _savedJobs: MutableLiveData<Resource<List<JobDto>>> = MutableLiveData()
    val saveJobs: LiveData<Resource<List<JobDto>>>
        get() = _savedJobs

    fun getSavedJobs() = viewModelScope.launch {
        _savedJobs.value = Resource.Loading
        _savedJobs.value = jobRepository.getSavedJobs()
    }

    fun filtJobs(filter: JobFilter) = viewModelScope.launch {
        _jobList.value = Resource.Loading
        _jobList.value = jobRepository.filtJobs(filter)
    }

    private val _locations: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    val locations: LiveData<Resource<List<String>>>
        get() = _locations

    fun getLocations() = viewModelScope.launch {
        _locations.value = Resource.Loading
        _locations.value = jobRepository.getLocations()
    }
}