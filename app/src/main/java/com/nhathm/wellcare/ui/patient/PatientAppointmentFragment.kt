package com.nhathm.wellcare.ui.patient

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.nhathm.wellcare.adapter.PageAdapter
import com.nhathm.wellcare.databinding.FragmentPatientAppointmentBinding
import com.nhathm.wellcare.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


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
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Upcoming")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Completed")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")
        pageAdapter.addWithTitle(PatientUpcomingAppointmentFragment(), "Canceled")

        binding.viewPager.adapter = pageAdapter
        binding.viewPager.currentItem = 0

        binding.tabLayout.setViewPager(binding.viewPager)

        binding.tabLayout.setOnTabClickListener() {
            Log.i("_xkxkx", "onTabClickListener")
        }

//        binding.tabLayout.set(binding.viewPager)
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.let {
//                    binding.viewPager.currentItem = it.position
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                // No action needed
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                // No action needed
//            }
//        })

        return binding.root
    }


}