package com.nhathm.wellcare.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nhathm.wellcare.databinding.FragmentOnBoardingBinding
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
                binding.title.text = "Book appointments in seconds with our easy-to-use platform"
                binding.animationView.setAnimation(com.nhathm.wellcare.R.raw.ill_1)
            }
            1 -> {
                binding.title.text = "Find trusted healthcare professionals and book appointments in a snap"
                binding.animationView.setAnimation(com.nhathm.wellcare.R.raw.ill_2)
            }
            2 -> {
                binding.title.text = "Skip the wait and schedule appointments with top-rated doctors"
                binding.animationView.setAnimation(com.nhathm.wellcare.R.raw.ill_3)
            }
            3 -> {
                binding.title.text = "Take control of your health with our app's simple appointment booking"
                binding.animationView.setAnimation(com.nhathm.wellcare.R.raw.ill_4)
            }
        }
    }

}