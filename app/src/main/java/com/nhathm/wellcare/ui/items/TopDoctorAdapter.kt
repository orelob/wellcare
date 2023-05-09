package com.nhathm.jobhunt.ui.jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.google.gson.Gson
import com.nhathm.wellcare.adapter.BaseAdapter
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.ItemTopDoctorBinding
import com.nhathm.wellcare.databinding.ItemUpcomingAppointmentBinding

class TopDoctorAdapter(private val doctorList: MutableList<Doctor>) :
    BaseAdapter<ItemTopDoctorBinding>(ItemTopDoctorBinding::inflate, doctorList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemTopDoctorBinding>, position: Int) {
        val doctor = doctorList[position]

    }
}