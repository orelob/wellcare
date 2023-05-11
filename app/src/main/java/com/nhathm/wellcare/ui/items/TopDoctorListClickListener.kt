package com.nhathm.wellcare.ui.items

import android.view.View
import com.nhathm.wellcare.data.Doctor

interface TopDoctorListClickListener {
    fun onDoctorListItemClick(view: View, doctor: Doctor)
}