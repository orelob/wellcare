package com.nhathm.wellcare.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.nhathm.wellcare.R
import com.nhathm.wellcare.adapter.PageAdapter
import com.nhathm.wellcare.databinding.ActivityPatientBinding
import com.nhathm.wellcare.ui.doctor.DoctorDetailFragment
import com.nhathm.wellcare.ui.patient.PatientAppointmentFragment
import com.nhathm.wellcare.ui.patient.PatientHomeFragment
import com.nhathm.wellcare.ui.patient.PatientSettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val page = PageAdapter(supportFragmentManager)

        page.adds(
            PatientHomeFragment(),
            PatientAppointmentFragment(),
            PatientSettingFragment(),
        )

        binding.viewPager.offscreenPageLimit = 3

        binding.viewPager.adapter = page
        binding.viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_patient_home -> binding.viewPager.currentItem = 0
                R.id.navigation_patient_appointment -> binding.viewPager.currentItem = 1
                R.id.navigation_patient_settings -> binding.viewPager.currentItem = 2
            }
            renderFragment()
            true
        }
    }

    private fun renderFragment() {
        val layoutParams = binding.bottomNavigation.layoutParams as CoordinatorLayout.LayoutParams
        val bottomNavigation = layoutParams.behavior as HideBottomViewOnScrollBehavior
        bottomNavigation.slideUp(binding.bottomNavigation)
    }
}