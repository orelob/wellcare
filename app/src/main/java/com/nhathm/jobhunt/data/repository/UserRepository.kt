package com.nhathm.jobhunt.data.repository

import com.nhathm.jobhunt.base.BaseRepository
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.data.api.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val localDataStore: LocalDataStore
) : BaseRepository() {

    suspend fun getUserAuth() = safeApiCall {
        userApi.getUserAuth()
    }

}
