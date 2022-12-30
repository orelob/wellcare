package com.nhathm.jobhunt.ui.profile

import android.content.Intent
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.activity.JobDetailActivity
import com.nhathm.jobhunt.adapter.BaseAdapter
import com.nhathm.jobhunt.adapter.loadImage
import com.nhathm.jobhunt.data.AppliedJob
import com.nhathm.jobhunt.data.model.Education
import com.nhathm.jobhunt.databinding.ItemAppliedJobBinding
import com.nhathm.jobhunt.databinding.ItemEducationBinding

class EducationItem(private val educationItems: MutableList<Education>) :
    BaseAdapter<ItemEducationBinding>(ItemEducationBinding::inflate, educationItems) {

    override fun onBindViewHolder(holder: ViewHolder<ItemEducationBinding>, position: Int) {
        val education = educationItems[position]

        holder.binding.education.text = education.education
        holder.binding.degreeAndMajor.text =  education.degreeAndMajor
        holder.binding.graduation.text = education.graduation
        loadImage(holder.binding.photo, "https://avatars.githubusercontent.com/u/73708112?v=4")
    }
}