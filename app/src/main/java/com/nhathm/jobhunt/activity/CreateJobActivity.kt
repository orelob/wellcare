package com.nhathm.jobhunt.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.request.CreateJobRequest
import com.nhathm.jobhunt.databinding.ActivityCreateJobBinding
import com.nhathm.jobhunt.ui.recruiter_jobs.RecruiterJobsViewModel
import com.nhathm.jobhunt.utils.enable
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.startNewActivity
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateJobActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateJobBinding
    private val viewModel by viewModels<RecruiterJobsViewModel>()
    var jobType: String = "Fulltime"
    var workType: String = "In office"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    fun setUp() {
        binding.progressBar.visible(false)
        binding.backButton.setOnClickListener { onBackPressed() }
        binding.cancelButton.setOnClickListener { onBackPressed() }

        binding.title.addTextChangedListener {
            val title = binding.title.text.toString().trim()
            binding.createJobButton.enable(title.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.radio1.setOnClickListener {
            jobType = binding.radio1.text.toString()
        }
        binding.radio2.setOnClickListener {
            jobType = binding.radio2.text.toString()
        }
        binding.radio3.setOnClickListener {
            jobType = binding.radio3.text.toString()
        }
        binding.radio4.setOnClickListener {
            jobType = binding.radio4.text.toString()
        }

        binding.radioWorkType1.setOnClickListener {
            workType = binding.radioWorkType1.text.toString()
        }
        binding.radioWorkType2.setOnClickListener {
            workType = binding.radioWorkType2.text.toString()
        }
        binding.radioWorkType3.setOnClickListener {
            workType = binding.radioWorkType3.text.toString()
        }

        binding.createJobButton.setOnClickListener {
            createJob()
        }

        viewModel.job.observe(this, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(this@CreateJobActivity, "Create job successfully", Toast.LENGTH_SHORT).show()
                        binding.root.context.startActivity(
                            Intent(
                                binding.root.context,
                                RecruiterActivity::class.java
                            )
                        )
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this@CreateJobActivity, "Create job failed", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }

    fun createJob() {
        var title = binding.title.text.toString()
        var description = binding.description.text.toString()
        var skills = binding.skills.text.toString()
        var jobType = jobType
        var location = binding.location.text.toString()
        var salary = binding.minSalary.text.toString() + " " + binding.maxSalary.text.toString()
        var workType = workType
        var yearOfExperience = binding.yearOfExperience.text.toString()

        var request = CreateJobRequest(
            title,
            description,
            jobType,
            location,
            Integer.parseInt(binding.minSalary.text.toString().substring(1, binding.minSalary.text.toString().length - 1)),
            Integer.parseInt(binding.maxSalary.text.toString().substring(1, binding.minSalary.text.toString().length - 1)),
            workType,
            yearOfExperience,
            skills,
        )
        viewModel.createJob(request)
    }
}