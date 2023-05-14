package com.nhathm.wellcare.data.repository

import com.nhathm.wellcare.base.BaseRepository
import com.nhathm.wellcare.data.api.AppointmentApi
import com.nhathm.wellcare.data.api.ArticleApi
import com.nhathm.wellcare.data.api.DoctorApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleApi: ArticleApi
) : BaseRepository() {

    suspend fun getArticles() = safeApiCall {
        articleApi.getArticles()
    }
}