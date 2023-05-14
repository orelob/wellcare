package com.nhathm.wellcare.ui.patient.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nhathm.wellcare.base.BaseViewModel
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Article
import com.nhathm.wellcare.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlePatientHomeViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : BaseViewModel() {

    private val _articleList: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val articleList: LiveData<Resource<List<Article>>>
        get() = _articleList

    fun getArticles() = viewModelScope.launch {
        _articleList.value = Resource.Loading
        delay(2000)
        _articleList.value = articleRepository.getArticles()
    }

    init {
        getArticles()
    }
}