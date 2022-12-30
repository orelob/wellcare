package com.nhathm.jobhunt.ui.jobs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.nhathm.jobhunt.activity.ChangePasswordActivity
import com.nhathm.jobhunt.activity.CreateJobActivity
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.databinding.FragmentRecruiterJobsBinding
import com.nhathm.jobhunt.ui.recruiter_jobs.RecruiterJobs
import com.nhathm.jobhunt.ui.recruiter_jobs.RecruiterJobsViewModel
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.view.adapter
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecruiterJobsFragment : Fragment() {

    private lateinit var binding: FragmentRecruiterJobsBinding
    private val viewModel by viewModels<RecruiterJobsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruiterJobsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel.getJobs("")

        binding.searchButton.setOnClickListener() {
            viewModel.getJobs(binding.jobTitle.text.toString())
        }

        binding.postAJobButton.setOnClickListener {
            binding.root.context.startActivity(
                Intent(
                    binding.root.context,
                    CreateJobActivity::class.java
                )
            )
        }

        viewModel.jobList.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        var jobList = it.value as MutableList<JobDto>
                        it.value.let { it1 -> binding.jobList.adapter(RecruiterJobs(jobList), 1)

                        }
                        binding.numberOfResult.text = jobList.size.toString() + " job" + if (jobList.size > 1) "s" else ""
                        if (jobList.size == 0) {
                            binding.noResult.visibility = View.VISIBLE
                        } else {
                            binding.noResult.visibility = View.GONE
                        }
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {}
            }
        })

    }
}