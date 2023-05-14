package com.nhathm.jobhunt.ui.jobs

import android.content.Intent
import com.nhathm.wellcare.R
import com.nhathm.wellcare.activity.DoctorDetailActivity
import com.nhathm.wellcare.adapter.BaseAdapter
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.ItemDoctorBinding


class DoctorAdapter(private val doctorList: MutableList<Doctor>) :
    BaseAdapter<ItemDoctorBinding>(ItemDoctorBinding::inflate, doctorList) {

    private var callback: DoctorAdapterCallback? = null

    override fun onBindViewHolder(holder: ViewHolder<ItemDoctorBinding>, position: Int) {
        val doctor = doctorList[position]
        if (doctor.saved) {
            holder.binding.saveDoctorButton.setImageResource(R.drawable.ic_bookmark_active_24)
        } else {
            holder.binding.saveDoctorButton.setImageResource(R.drawable.ic_bookmark_inactive_24)
        }


        holder.binding.saveDoctorButton.setOnClickListener {
            callback?.onDoctorSaved(position, doctor)

        }

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

    fun updateItem(position: Int, updatedDoctor: Doctor) {
        doctorList.set(position, updatedDoctor)
        notifyItemChanged(position)
    }

    fun setCallback(callback: DoctorAdapterCallback?) {
        this.callback = callback
    }

    interface DoctorAdapterCallback {
        fun onDoctorSaved(position: Int, doctor: Doctor)
    }
}