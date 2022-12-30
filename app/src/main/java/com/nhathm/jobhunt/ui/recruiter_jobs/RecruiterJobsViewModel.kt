package com.nhathm.jobhunt.ui.recruiter_jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.jobhunt.base.BaseViewModel
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.UserRepository
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.data.repository.JobRepository
import com.nhathm.jobhunt.data.request.CreateJobRequest
import com.nhathm.jobhunt.data.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruiterJobsViewModel @Inject constructor(
    private val jobRepository: JobRepository
) :
    BaseViewModel() {

    private val _jobList: MutableLiveData<Resource<List<JobDto>>> = MutableLiveData()
    val jobList: LiveData<Resource<List<JobDto>>>
        get() = _jobList

    private val _job: MutableLiveData<Resource<JobDto>> = MutableLiveData()
    val job: LiveData<Resource<JobDto>>
        get() = _job

    private val _saveJob: MutableLiveData<Resource<JobDto>> = MutableLiveData()
    val saveJob: LiveData<Resource<JobDto>>
        get() = _saveJob

    private val _appliedJob: MutableLiveData<Resource<JobDto>> = MutableLiveData()
    val appliedJob: LiveData<Resource<JobDto>>
        get() = _appliedJob

    fun getJobs(text: String) = viewModelScope.launch {
        _jobList.value = Resource.Loading
        _jobList.value = jobRepository.getJobs(text)
    }

    fun createJob(job: CreateJobRequest) = viewModelScope.launch {
        _job.value = Resource.Loading
        _job.value = jobRepository.createJob(job)
    }

    fun saveJob(job: JobDto) = viewModelScope.launch {
        _saveJob.value = Resource.Loading
        _saveJob.value = jobRepository.saveJob(job)
    }

    fun applyJob(job: JobDto) = viewModelScope.launch {
        _appliedJob.value = Resource.Loading
        _appliedJob.value = jobRepository.applyJob(job)
    }
}