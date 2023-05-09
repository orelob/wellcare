package com.nhathm.wellcare.ui.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nhathm.wellcare.activity.*
import com.nhathm.wellcare.base.BaseFragment
import com.nhathm.wellcare.databinding.FragmentDoctorSettingsBinding
import com.nhathm.wellcare.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorMenuFragment() : BaseFragment() {

    private lateinit var binding: FragmentDoctorSettingsBinding
    private val viewModel by viewModels<DoctorMenuViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDoctorSettingsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDoctorSettingsBinding.bind(view)

        binding.logoutButton.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        viewModel.logout()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }
}