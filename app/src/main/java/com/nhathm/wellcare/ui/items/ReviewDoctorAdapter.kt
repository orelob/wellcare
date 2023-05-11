package com.nhathm.jobhunt.ui.jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.google.gson.Gson
import com.nhathm.wellcare.adapter.BaseAdapter
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.ItemDoctorBinding
import com.nhathm.wellcare.databinding.ItemDoctorReviewBinding
import com.nhathm.wellcare.databinding.ItemTopDoctorBinding
import com.nhathm.wellcare.databinding.ItemUpcomingAppointmentBinding

class ReviewDoctorAdapter(private val doctorList: MutableList<String>) :
    BaseAdapter<ItemDoctorReviewBinding>(ItemDoctorReviewBinding::inflate, doctorList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemDoctorReviewBinding>, position: Int) {
        val doctor = doctorList[position]

    }
}