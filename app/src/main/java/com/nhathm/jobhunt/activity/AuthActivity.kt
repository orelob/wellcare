package com.nhathm.jobhunt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.adapter.PageAdapter
import com.nhathm.jobhunt.databinding.ActivityAuthBinding
import com.nhathm.jobhunt.ui.auth.LoginFragment
import com.nhathm.jobhunt.ui.auth.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val page = PageAdapter(supportFragmentManager)
        page.adds(LoginFragment(), SignUpFragment())

        binding.viewPager.adapter = page
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.currentItem = 0

    }

}