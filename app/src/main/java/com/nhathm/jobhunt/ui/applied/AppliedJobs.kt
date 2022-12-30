package com.nhathm.jobhunt.ui.jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.R.*
import com.nhathm.jobhunt.activity.JobDetailActivity
import com.nhathm.jobhunt.adapter.BaseAdapter
import com.nhathm.jobhunt.adapter.loadImage
import com.nhathm.jobhunt.data.AppliedJob
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.databinding.ItemAppliedJobBinding
import com.nhathm.jobhunt.databinding.ItemJobBinding

class AppliedJobs(private val jobList: MutableList<JobDto>) :
    BaseAdapter<ItemAppliedJobBinding>(ItemAppliedJobBinding::inflate, jobList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemAppliedJobBinding>, position: Int) {
        val job = jobList[position]

        holder.binding.companyName.text = job.company.name
        holder.binding.jobTitle.text = job.job.title
        holder.binding.status.text = "Pending"
        loadImage(holder.binding.photo, job.company.logoUrl)

        holder.itemView.setOnClickListener {
            holder.binding.root.context.startActivity(
                Intent(
                    holder.binding.root.context,
                    JobDetailActivity::class.java
                ).putExtra(Constant.DATA, Gson().toJson(job))
            )
        }
    }
}