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
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.databinding.ItemJobBinding

class RecommendJobs(private val jobList: MutableList<JobDto>) :
    BaseAdapter<ItemJobBinding>(ItemJobBinding::inflate, jobList) {

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder<ItemJobBinding>, position: Int) {

        Log.i("JobList-json", Gson().toJson(jobList[position]))

        val job = jobList[position]
        holder.binding.title.text = job.job.title
        holder.binding.location.text = job.job.location

        holder.binding.salary.text = job.job.minSalary.toString() + "$ - " + job.job.maxSalary.toString() + "$"
        holder.binding.createAt.text =  job.job.createdAt.substring(0, 10)
        holder.binding.companyName.text = job.company.name
        holder.binding.overview.text = job.company.overview

        if (job.applied) {
            holder.binding.applyButton.text = "Applied"
        } else {
            holder.binding.applyButton.text = "Apply"
        }

        if (job.saved) {
            holder.binding.saveButton.text = "Saved"
        } else {
            holder.binding.saveButton.text = "Save"
        }

        loadImage(holder.binding.photo, job.company.logoUrl)

        holder.itemView.setOnClickListener {
            holder.binding.root.context.startActivity(
                Intent(
                    holder.binding.root.context,
                    JobDetailActivity::class.java
                ).putExtra(Constant.DATA, Gson().toJson(job))
            )
        }

        holder.binding.title.setOnClickListener {
            holder.binding.title.setTextColor(Color.parseColor("#01A2D5"))
            Log.i("Switch detail", "Switch to detail")
            context.startActivity(
                Intent(
                    context,
                    JobDetailActivity::class.java
                ).putExtra(Constant.DATA, Gson().toJson(job))
            )
        }
    }
}