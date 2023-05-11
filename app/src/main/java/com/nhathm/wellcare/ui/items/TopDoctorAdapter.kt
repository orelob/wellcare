package com.nhathm.jobhunt.ui.jobs

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nhathm.wellcare.R
import com.nhathm.wellcare.activity.DoctorDetailActivity
import com.nhathm.wellcare.adapter.BaseAdapter
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.ItemTopDoctorBinding
import com.nhathm.wellcare.ui.doctor.DoctorDetailFragment
import com.nhathm.wellcare.ui.items.TopDoctorListClickListener


class TopDoctorAdapter(private val doctorList: MutableList<Doctor>, val topDoctorListClickListener: TopDoctorListClickListener) :
    BaseAdapter<ItemTopDoctorBinding>(ItemTopDoctorBinding::inflate, doctorList) {

    override fun onBindViewHolder(holder: ViewHolder<ItemTopDoctorBinding>, position: Int) {
        val doctor = doctorList[position]

        holder.itemView
            .setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        DoctorDetailActivity::class.java
                    )
                )
            }
    }
}