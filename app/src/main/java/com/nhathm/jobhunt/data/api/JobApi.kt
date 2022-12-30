package com.nhathm.jobhunt.data.api

import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.data.request.CreateJobRequest
import com.nhathm.jobhunt.data.request.JobFilter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JobApi {

    @POST("jobs/jobs-by-company")
    suspend fun getJobs(@Body text: String): List<JobDto>

    @GET("jobs/applied-jobs")
    suspend fun getAppliedJobs(): List<JobDto>

    @GET("jobs/saved-jobs")
    suspend fun getSavedJobs(): List<JobDto>

    @POST("jobs/filter")
    suspend fun filtJobs(@Body jobFilter: JobFilter): List<JobDto>

    @POST("jobs/create")
    suspend fun createJob(@Body request: CreateJobRequest): JobDto

    @POST("jobs/apply")
    suspend fun applyJob(@Body request: JobDto): JobDto

    @POST("jobs/save")
    suspend fun saveJob(@Body request: JobDto): JobDto

    @GET("jobs/locations")
    suspend fun getLocations(): List<String>
}