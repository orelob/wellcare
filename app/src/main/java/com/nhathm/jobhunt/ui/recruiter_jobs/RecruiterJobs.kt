package com.nhathm.jobhunt.ui.recruiter_jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.R.*
import com.nhathm.jobhunt.activity.JobDetailActivity
import com.nhathm.jobhunt.adapter.BaseAdapter
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.databinding.ItemJobBasicInfoBinding
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


class RecruiterJobs(private val jobList: MutableList<JobDto>) :
    BaseAdapter<ItemJobBasicInfoBinding>(ItemJobBasicInfoBinding::inflate, jobList) {

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder<ItemJobBasicInfoBinding>, position: Int) {

        Log.i("JobList-json", Gson().toJson(jobList[position]))

        val job = jobList[position]
        holder.binding.jobTitle.text = job.job.title
        holder.binding.location.text = job.job.location
        holder.binding.createAt.text =  job.job.createdAt.substring(0, 10)

        holder.binding.card.setOnClickListener {
            holder.binding.root.context.startActivity(
                Intent(
                    holder.binding.root.context,
                    JobDetailActivity::class.java
                ).putExtra(Constant.DATA, Gson().toJson(job))
            )
        }
    }
}