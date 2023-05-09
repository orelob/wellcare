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
import com.nhathm.wellcare.databinding.ItemTopDoctorBinding
import com.nhathm.wellcare.databinding.ItemUpcomingAppointmentBinding

class DoctorAdapter(private val doctorList: MutableList<Doctor>) :
    BaseAdapter<ItemDoctorBinding>(ItemDoctorBinding::inflate, doctorList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemDoctorBinding>, position: Int) {
        val doctor = doctorList[position]

    }
}