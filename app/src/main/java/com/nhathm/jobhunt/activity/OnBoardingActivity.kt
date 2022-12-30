package com.nhathm.jobhunt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.viewpager.widget.ViewPager
import com.nhathm.jobhunt.adapter.PageAdapter
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.databinding.ActivityOnBoardingBinding
import com.nhathm.jobhunt.ui.onboarding.OnBoardingFragment
import com.nhathm.jobhunt.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val page = PageAdapter(supportFragmentManager)
        page.adds(OnBoardingFragment(0), OnBoardingFragment(1), OnBoardingFragment(2))

        binding.viewPager.adapter = page
        binding.dotsIndicator.setViewPager(binding.viewPager)
        var currentPage = 0
        binding.buttonGetStarted.setOnClickListener {
            if (currentPage < 2) {
                currentPage += 1
                binding.viewPager.currentItem = currentPage
            } else {
                finish()
                val localDataStore = LocalDataStore(this)
                localDataStore.accessToken.asLiveData().observe(this, Observer {
                    val activity = if (it == null) AuthActivity::class.java else MainActivity::class.java
                    startNewActivity(activity)
                })
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
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        binding.textSkipIntro.setOnClickListener {
            finish()
            // Check if user has already seen logged in
            val localDataStore = LocalDataStore(this)
            localDataStore.accessToken.asLiveData().observe(this, Observer {
                val activity = if (it == null) AuthActivity::class.java else MainActivity::class.java
                startNewActivity(activity)
            })
        }
    }
}