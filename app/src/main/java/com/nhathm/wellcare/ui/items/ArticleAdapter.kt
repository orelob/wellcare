package com.nhathm.jobhunt.ui.jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.google.gson.Gson
import com.nhathm.wellcare.adapter.BaseAdapter
import com.nhathm.wellcare.adapter.loadImage
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Article
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.ItemArticleBinding

class ArticleAdapter(private val articleList: MutableList<Article>) :
    BaseAdapter<ItemArticleBinding>(ItemArticleBinding::inflate, articleList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemArticleBinding>, position: Int) {
        val article = articleList[position]
        loadImage(holder.binding.thumbnail, article.thumbnail)
        holder.binding.title.text = article.title
    }
}