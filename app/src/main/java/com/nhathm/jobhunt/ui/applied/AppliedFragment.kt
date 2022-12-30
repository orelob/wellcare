package com.nhathm.jobhunt.ui.applied

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.base.BaseFragment
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.AppliedJob
import com.nhathm.jobhunt.data.model.Job
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.databinding.FragmentAppliedBinding
import com.nhathm.jobhunt.ui.jobs.AppliedJobs
import com.nhathm.jobhunt.ui.jobs.JobsViewModel
import com.nhathm.jobhunt.ui.jobs.RecommendJobs
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.view.adapter
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AppliedFragment : BaseFragment() {

    private lateinit var binding: FragmentAppliedBinding
    private val viewModel by viewModels<JobsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppliedBinding.inflate(layoutInflater)

        viewModel.getAppliedJobs()
        viewModel.getSavedJobs()

        binding.title.setOnClickListener {
            viewModel.getAppliedJobs()
            viewModel.getSavedJobs()
        }
        binding.title2.setOnClickListener {
            viewModel.getAppliedJobs()
            viewModel.getSavedJobs()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.appliedJobList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        var jobList = it.value as MutableList<JobDto>
                        it.value.let { it1 -> binding.appliedJobs.adapter(AppliedJobs(jobList), 1)

                        }
                        binding.title.text = "Applied: " + jobList.size.toString() + " Job" + if (jobList.size > 1) "s" else ""
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {}
            }
        })

        viewModel.saveJobs.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        var jobList = it.value as MutableList<JobDto>
                        it.value.let { it1 -> binding.savedJobs.adapter(RecommendJobs(jobList), 1)

                        }
                        binding.title2.text = "Saved: " + jobList.size.toString() + " Job" + if (jobList.size > 1) "s" else ""
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