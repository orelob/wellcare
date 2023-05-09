package com.nhathm.wellcare.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.viewpager.widget.ViewPager
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.nhathm.wellcare.adapter.PageAdapter
import com.nhathm.wellcare.data.LocalDataStore
import com.nhathm.wellcare.data.SharedPreferencesManager
import com.nhathm.wellcare.databinding.ActivityOnBoardingBinding
import com.nhathm.wellcare.ui.onboarding.OnBoardingFragment
import com.nhathm.wellcare.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val page = PageAdapter(supportFragmentManager)
        page.adds(
            OnBoardingFragment(0),
            OnBoardingFragment(1),
            OnBoardingFragment(2),
            OnBoardingFragment(3)
        )

        binding.viewPager.adapter = page
        binding.dotsIndicator.setViewPager(binding.viewPager)
        var currentPage = 0
        binding.buttonGetStarted.setOnClickListener {
            if (currentPage < 3) {
                currentPage += 1
                binding.viewPager.currentItem = currentPage
            } else {
                finish()
                render()
            }
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> currentPage = 0
                    1 -> currentPage = 1
                    2 -> currentPage = 2
                    3 -> currentPage = 3
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.textSkipIntro.setOnClickListener {
            finish()
            render()
        }
    }

    private fun render() {
        SharedPreferencesManager.getRole()?.let { Log.i("WHATROLE", it) }

        val activity =
            if (SharedPreferencesManager.getAuthToken() == null)
                AuthActivity::class.java
            else if (SharedPreferencesManager.getRole().equals("DOCTOR"))
                DoctorActivity::class.java
            else
                PatientActivity::class.java

        startNewActivity(activity)
    }
}