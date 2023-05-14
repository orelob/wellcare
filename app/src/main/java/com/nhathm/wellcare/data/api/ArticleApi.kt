package com.nhathm.wellcare.data.api

import com.nhathm.wellcare.data.Article
import com.nhathm.wellcare.data.request.*
import retrofit2.http.*

interface ArticleApi {

    @GET("articles/get-articles")
    suspend fun getArticles(): List<Article>
}

