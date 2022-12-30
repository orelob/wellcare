package com.nhathm.jobhunt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.adapter.PageAdapter
import com.nhathm.jobhunt.adapter.loadImage
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.databinding.ActivityAuthBinding
import com.nhathm.jobhunt.databinding.ActivityJobDetailBinding
import com.nhathm.jobhunt.ui.auth.AuthViewModel
import com.nhathm.jobhunt.ui.jobs.JobsViewModel
import com.nhathm.jobhunt.ui.recruiter_jobs.RecruiterJobsViewModel
import com.nhathm.jobhunt.ui.user.UserViewModel
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailBinding

    private val jobViewModel by viewModels<RecruiterJobsViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        val job = Gson().fromJson(intent.getStringExtra(Constant.DATA), JobDto::class.java)

        binding.title.text = job.job.title
        loadImage(binding.photo, job.company.logoUrl)

        binding.overview.text = job.company.overview
        binding.jobDescription.text = job.job.description
        binding.companyName.text = job.company.name
        binding.location.text = job.job.location
        binding.skills.text = job.job.skills.joinToString(", ")
        binding.yearOfExperience.text = job.job.yearsOfExperience
        binding.companySize.text = job.company.companySize

        binding.contactName.text = job.hiringContact.fullName
        binding.role.text = job.hiringContact.companyRole
        binding.email.text = job.hiringContact.email

        job.hiringContact.avatarUrl?.let { loadImage(binding.avatarUrl, it) }

        if (job.applied) {
            binding.applyButton.text = "Applied"
        } else {
            binding.applyButton.text = "Apply"
        }

        if (job.saved) {
            binding.saveButton.text = "Saved"
        } else {
            binding.saveButton.text = "Save"
        }

        binding.applyButton.setOnClickListener() {
            applyJob(job)
        }

        binding.saveButton.setOnClickListener {
            saveJob(job)
        }

        jobViewModel.appliedJob.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(this@JobDetailActivity, "Successfully", Toast.LENGTH_SHORT).show()
                        if (binding.applyButton.text == "Apply") {
                            binding.applyButton.text = "Applied"
                        } else {
                            binding.applyButton.text = "Apply"
                        }
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })

        jobViewModel.saveJob.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(this@JobDetailActivity, "Successfully", Toast.LENGTH_SHORT).show()
                        if (binding.saveButton.text == "Save") {
                            binding.saveButton.text = "*"
                        } else {
                            binding.saveButton.text = "Save"
                        }
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }

    fun saveJob(job: JobDto) {
        jobViewModel.saveJob(job)
    }

    fun applyJob(job: JobDto) {
        jobViewModel.applyJob(job)
    }
}