package com.nhathm.wellcare.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.nhathm.wellcare.R
import com.nhathm.wellcare.adapter.PageAdapter
import com.nhathm.wellcare.databinding.ActivityDoctorBinding
import com.nhathm.wellcare.ui.doctor.DoctorHomeFragment
import com.nhathm.wellcare.ui.doctor.DoctorMenuFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val page = PageAdapter(supportFragmentManager)

        page.adds(DoctorHomeFragment(), DoctorMenuFragment())

        binding.viewPager.adapter = page
        binding.viewPager.offscreenPageLimit = 5
        binding.viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.bottomNavigation.menu.getItem(0).isChecked = true
                    }
                    1 -> {
                        binding.bottomNavigation.menu.getItem(1).isChecked = true
                    }
//                    2 -> {
//                        binding.bottomNavigation.menu.getItem(2).isChecked = true
//                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_doctor_home -> binding.viewPager.currentItem = 0
//                R.id.navigation_doctor_profile -> binding.viewPager.currentItem = 1
                R.id.navigation_doctor_setting -> binding.viewPager.currentItem = 1
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