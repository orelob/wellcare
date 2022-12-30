package com.nhathm.jobhunt.ui.jobs

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nhathm.jobhunt.R as myR
import android.R
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.data.request.JobFilter
import com.nhathm.jobhunt.databinding.FragmentJobsBinding
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.view.adapter
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.florescu.android.rangeseekbar.RangeSeekBar


@AndroidEntryPoint
class JobsFragment : Fragment() {

    private lateinit var binding: FragmentJobsBinding
    private val viewModel by viewModels<JobsViewModel>()

    var filter = JobFilter("", 1, 200000, "", arrayListOf() , arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobsBinding.inflate(layoutInflater)

        binding.expandableLayout.visible(false)
        binding.filterButton.setOnClickListener {
            if (binding.expandableLayout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.expandableLayout.visible(true)
                binding.filterButton.setImageResource(myR.drawable.ic_baseline_filter_list_active_24)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.expandableLayout.visible(false)
                binding.filterButton.setImageResource(myR.drawable.ic_baseline_filter_list_24)
            }
        }

        filter.jobTypes += "Fulltime"
        filter.jobTypes += "Contract"
        filter.jobTypes += "Internship"
        filter.jobTypes += "Cofounder"

        filter.companySizes += "1-10 employees"
        filter.companySizes += "11-50 employees"
        filter.companySizes += "51-200 employees"
        filter.companySizes += "201-500 employees"
        filter.companySizes += "501+ employees"

        val fruits = arrayOf("Hanoi",
            "Ho Chi Minh City",
            "Da Nang",
            "New York",
            "Tokyo")

        viewModel.getLocations()

        viewModel.locations.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        var locations = it.value as MutableList<String>
                        val adapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_dropdown_item, locations)
                        binding.autoCompleteLocation.threshold = 1
                        binding.autoCompleteLocation.setAdapter(adapter)
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {}
            }
        })

        binding.searchButton.setOnClickListener {
            viewModel.filtJobs(filter)
        }

        viewModel.filtJobs(filter)
        binding.seekbarPlaceholder.setRangeValues(1, 100000)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpLocation()
        setUpSalaryRange()
        setUpTitleSearch()
        setUpJobTypes()
        setUpCompanySizes()

        viewModel.filtJobs(filter)

        viewModel.jobList.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        var jobList = it.value as MutableList<JobDto>
                        it.value.let { it1 -> binding.savedJobs.adapter(RecommendJobs(jobList), 1)

                        }
                        binding.numberOfResult.text = jobList.size.toString() + " result" + if (jobList.size > 1) "s" else ""
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {}
            }
        })
    }

    fun setUpLocation() {
        binding.autoCompleteLocation.addTextChangedListener() {
            filter.location = it.toString()
            viewModel.filtJobs(filter)
        }
    }

    fun setUpSalaryRange() {
        binding.seekbarPlaceholder.setOnRangeSeekBarChangeListener { bar, minValue, maxValue ->
            run {
                binding.salaryTitle.text =
                    "Salary ($): "
                filter.minSalary = bar.selectedMinValue.toInt()
                filter.maxSalary = bar.selectedMaxValue.toInt()
                viewModel.filtJobs(filter)
            }
        }
    }

    fun setUpTitleSearch() {
        binding.jobTitle.addTextChangedListener {
            filter.title = it.toString()
            viewModel.filtJobs(filter)
        }
    }

    fun setUpJobTypes() {
        binding.check1.setOnClickListener {
            if (binding.check1.isChecked) {
                filter.jobTypes += binding.check1.text.toString()
            } else {
                filter.jobTypes -= binding.check1.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.check2.setOnClickListener {
            if (binding.check1.isChecked) {
                filter.jobTypes += binding.check2.text.toString()
            } else {
                filter.jobTypes -= binding.check2.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.check3.setOnClickListener {
            if (binding.check1.isChecked) {
                filter.jobTypes += binding.check3.text.toString()
            } else {
                filter.jobTypes -= binding.check3.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.check4.setOnClickListener {
            if (binding.check1.isChecked) {
                filter.jobTypes += binding.check4.text.toString()
            } else {
                filter.jobTypes -= binding.check4.text.toString()
            }
            viewModel.filtJobs(filter)
        }
    }

    fun setUpCompanySizes() {
        binding.checkbox1.setOnClickListener {
            if (binding.checkbox1.isChecked) {
                filter.companySizes += binding.checkbox1.text.toString()
            } else {
                filter.companySizes -= binding.checkbox1.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.checkbox2.setOnClickListener {
            if (binding.checkbox2.isChecked) {
                filter.companySizes += binding.checkbox2.text.toString()
            } else {
                filter.companySizes -= binding.checkbox2.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.checkbox3.setOnClickListener {
            if (binding.checkbox3.isChecked) {
                filter.companySizes += binding.checkbox3.text.toString()
            } else {
                filter.companySizes -= binding.checkbox3.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.checkbox4.setOnClickListener {
            if (binding.checkbox4.isChecked) {
                filter.companySizes += binding.checkbox4.text.toString()
            } else {
                filter.companySizes -= binding.checkbox4.text.toString()
            }
            viewModel.filtJobs(filter)
        }
        binding.checkbox5.setOnClickListener {
            if (binding.checkbox5.isChecked) {
                filter.companySizes += binding.checkbox5.text.toString()
            } else {
                filter.companySizes -= binding.checkbox5.text.toString()
            }
            viewModel.filtJobs(filter)
        }
    }
}