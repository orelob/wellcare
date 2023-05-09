package com.nhathm.wellcare.ui.patient

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nhathm.wellcare.R as myR
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nhathm.jobhunt.ui.jobs.TopDoctorAdapter
import com.nhathm.jobhunt.ui.jobs.UpcomingAppointmentAdapter
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.data.api.AppointmentApi
import com.nhathm.wellcare.data.response.SignInResponse
import com.nhathm.wellcare.databinding.FragmentPatientHomeBinding
import com.nhathm.wellcare.utils.castToList
import com.nhathm.wellcare.utils.castToObject
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.view.adapter
import com.nhathm.wellcare.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PatientAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentPatientHomeBinding
    private val viewModel by viewModels<PatientViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientHomeBinding.inflate(layoutInflater)

        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.upcomingAppointmentList.observe(viewLifecycleOwner, Observer { appointments ->
            binding.progressbar.visible(appointments is Resource.Loading)
            when (appointments) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val appointmentsResponse =
                            castToList(appointments.value, Appointment::class.java);

                        binding.upcomingAppointments.adapter(
                            UpcomingAppointmentAdapter(appointmentsResponse as MutableList<Appointment>),
                            1
                        )
                    }
                }
                is Resource.Failure -> {
                    handleApiError(appointments)
                }
                else -> {}
            }
        })

        viewModel.topDoctorList.observe(viewLifecycleOwner, Observer { doctors ->
            binding.progressbar.visible(doctors is Resource.Loading)
            when (doctors) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val doctorsResponse =
                            castToList(doctors.value, Doctor::class.java);

                        binding.topDoctors.adapter(
                            TopDoctorAdapter(doctorsResponse as MutableList<Doctor>),
                            false, false
                        )
                    }
                }
                is Resource.Failure -> {
                    handleApiError(doctors)
                }
                else -> {}
            }
        })
    }

}