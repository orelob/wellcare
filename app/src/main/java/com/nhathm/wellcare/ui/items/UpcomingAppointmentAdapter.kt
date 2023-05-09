package com.nhathm.jobhunt.ui.jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.google.gson.Gson
import com.nhathm.wellcare.adapter.BaseAdapter
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.databinding.ItemUpcomingAppointmentBinding

class UpcomingAppointmentAdapter(private val jobList: MutableList<Appointment>) :
    BaseAdapter<ItemUpcomingAppointmentBinding>(ItemUpcomingAppointmentBinding::inflate, jobList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemUpcomingAppointmentBinding>, position: Int) {
        val job = jobList[position]
//
//        if (job.applied) {
//            holder.binding.applyButton.text = "Applied"
//        } else {
//            holder.binding.applyButton.text = "Apply"
//        }
//
//        if (job.saved) {
//            holder.binding.saveButton.text = "Saved"
//        } else {
//            holder.binding.saveButton.text = "Save"
//        }
//
//        loadImage(holder.binding.photo, job.company.logoUrl)
//
//        holder.itemView.setOnClickListener {
//            holder.binding.root.context.startActivity(
//                Intent(
//                    holder.binding.root.context,
//                    JobDetailActivity::class.java
//                ).putExtra(Constant.DATA, Gson().toJson(job))
//            )
//        }
//
//        holder.binding.title.setOnClickListener {
//            holder.binding.title.setTextColor(Color.parseColor("#01A2D5"))
//            Log.i("Switch detail", "Switch to detail")
//            context.startActivity(
//                Intent(
//                    context,
//                    JobDetailActivity::class.java
//                ).putExtra(Constant.DATA, Gson().toJson(job))
//            )
//        }
    }
}