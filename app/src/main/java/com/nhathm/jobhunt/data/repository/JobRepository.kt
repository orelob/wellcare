package com.nhathm.jobhunt.data.repository

import android.location.Location
import com.nhathm.jobhunt.base.BaseRepository
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.data.api.AuthApi
import com.nhathm.jobhunt.data.api.JobApi
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.data.request.CreateJobRequest
import com.nhathm.jobhunt.data.request.JobFilter
import com.nhathm.jobhunt.data.request.LoginRequest
import com.nhathm.jobhunt.data.request.SignUpRequest
import com.nhathm.jobhunt.data.response.LoginResponse
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val jobApi: JobApi,
    private val localDataStore: LocalDataStore
) : BaseRepository() {

    suspend fun getJobs(text: String) = safeApiCall {
        jobApi.getJobs(text)
    }

    suspend fun getAppliedJobs() = safeApiCall {
        jobApi.getAppliedJobs()
    }

    suspend fun getSavedJobs() = safeApiCall {
        jobApi.getSavedJobs()
    }

    suspend fun filtJobs(filter: JobFilter) = safeApiCall {
        jobApi.filtJobs(filter)
    }

    suspend fun createJob(job: CreateJobRequest) = safeApiCall {
        jobApi.createJob(job)
    }

    suspend fun applyJob(job: JobDto) = safeApiCall {
        jobApi.applyJob(job)
    }

    suspend fun saveJob(job: JobDto) = safeApiCall {
        jobApi.saveJob(job)
    }

    suspend fun getLocations() = safeApiCall {
        jobApi.getLocations()
    }
}