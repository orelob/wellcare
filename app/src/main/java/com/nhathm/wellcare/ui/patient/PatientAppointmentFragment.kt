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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.nhathm.jobhunt.ui.jobs.TopDoctorAdapter
import com.nhathm.jobhunt.ui.jobs.UpcomingAppointmentAdapter
import com.nhathm.wellcare.adapter.PageAdapter
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.data.api.AppointmentApi
import com.nhathm.wellcare.data.response.SignInResponse
import com.nhathm.wellcare.databinding.FragmentPatientAppointmentBinding
import com.nhathm.wellcare.databinding.FragmentPatientHomeBinding
import com.nhathm.wellcare.ui.auth.OtpVerificationFragment
import com.nhathm.wellcare.ui.doctor.DoctorDetailFragment
import com.nhathm.wellcare.utils.castToList
import com.nhathm.wellcare.utils.castToObject
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.view.adapter
import com.nhathm.wellcare.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PatientAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentPatientAppointmentBinding
    private val viewModel by viewModels<PatientViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientAppointmentBinding.inflate(layoutInflater)


        val pageAdapter = PageAdapter(childFragmentManager)

        pageAdapter.addWithTitle(PatientBookAppointmentFragment(), "Upcoming")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Completed")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")

        binding.viewPager.adapter = pageAdapter
        binding.viewPager.currentItem = 0
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // No action needed
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No action needed
            }
        })

        return binding.root
    }


}