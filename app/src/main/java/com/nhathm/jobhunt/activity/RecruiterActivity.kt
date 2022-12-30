package com.nhathm.jobhunt.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.adapter.PageAdapter
import com.nhathm.jobhunt.databinding.ActivityRecuiterBinding
import com.nhathm.jobhunt.ui.jobs.RecruiterJobsFragment
import com.nhathm.jobhunt.ui.menu.MenuFragment
import com.nhathm.jobhunt.ui.recruiter_company.RecruiterCompanyFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecruiterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecuiterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuiterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val page = PageAdapter(supportFragmentManager)
        var menu = MenuFragment().apply {
            arguments = Bundle().apply {
                putBoolean("isRecruiter", true)
            }
        }
        page.adds(RecruiterJobsFragment(), RecruiterCompanyFragment(), menu)

        binding.viewPager.adapter = page
        binding.viewPager.offscreenPageLimit = 3

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
                    2 -> {
                        binding.bottomNavigation.menu.getItem(2).isChecked = true
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_recruiter_jobs -> binding.viewPager.currentItem = 0
                R.id.navigation_recruiter_company -> binding.viewPager.currentItem = 1
                R.id.navigation_menu -> binding.viewPager.currentItem = 2
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