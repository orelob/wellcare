package com.nhathm.jobhunt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.databinding.ActivityAppliedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppliedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppliedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppliedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}