package com.nhathm.jobhunt.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nhathm.jobhunt.databinding.FragmentOnBoardingBinding
import com.nhathm.jobhunt.utils.html
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment constructor(private val index: Int): Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (index) {
            0 -> {
                binding.title.text = "Find the job made for you"
                binding.image.setImageResource(com.nhathm.jobhunt.R.drawable.onboarding_1)
            }
            1 -> {
                binding.title.text = "Hire candidates that want to help build your vision"
                binding.image.setImageResource(com.nhathm.jobhunt.R.drawable.onboarding_2)
            }
            2 -> {
                binding.title.text = "Let's start"
                binding.image.setImageResource(com.nhathm.jobhunt.R.drawable.onboarding_1)
            }
        }
    }

}