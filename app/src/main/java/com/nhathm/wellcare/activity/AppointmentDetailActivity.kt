package com.nhathm.wellcare.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nhathm.jobhunt.ui.jobs.ReviewDoctorAdapter
import com.nhathm.jobhunt.ui.jobs.TopDoctorAdapter
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.ActivityAppointmentDetailBinding
import com.nhathm.wellcare.databinding.ActivityDoctorDetailBinding
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.view.adapter
import com.nhathm.wellcare.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppointmentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        listener()
        observe()
    }

    fun listener() {

    }

    fun observe() {

    }
}