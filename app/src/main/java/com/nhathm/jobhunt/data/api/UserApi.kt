package com.nhathm.jobhunt.data.api

import com.nhathm.jobhunt.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users/by-auth")
    suspend fun getUserAuth(): User
}