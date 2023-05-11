package com.nhathm.wellcare.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nhathm.wellcare.databinding.ActivityBookAppointmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookAppointmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookAppointmentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        listener()
    }

    fun listener() {

    }
}