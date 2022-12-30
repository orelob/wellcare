package com.nhathm.jobhunt.ui.profile

import android.content.Intent
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.activity.JobDetailActivity
import com.nhathm.jobhunt.adapter.BaseAdapter
import com.nhathm.jobhunt.adapter.loadImage
import com.nhathm.jobhunt.data.AppliedJob
import com.nhathm.jobhunt.data.model.Experience
import com.nhathm.jobhunt.databinding.ItemAppliedJobBinding
import com.nhathm.jobhunt.databinding.ItemExperienceBinding

class ExperienceItem(private val jobList: MutableList<Experience>) :
    BaseAdapter<ItemExperienceBinding>(ItemExperienceBinding::inflate, jobList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemExperienceBinding>, position: Int) {
        val job = jobList[position]

        holder.binding.company.text = "Viettel Telecom"
        holder.binding.role.text = "Senior Android"
        loadImage(holder.binding.photo, "https://avatars.githubusercontent.com/u/73708112?v=4")

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