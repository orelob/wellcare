package com.nhathm.wellcare.ui.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nhathm.wellcare.activity.*
import com.nhathm.wellcare.base.BaseFragment
import com.nhathm.wellcare.databinding.FragmentPatientSettingsBinding
import com.nhathm.wellcare.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientSettingFragment() : BaseFragment() {

    private lateinit var binding: FragmentPatientSettingsBinding
    private val viewModel by viewModels<PatientSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPatientSettingsBinding.bind(view)

        binding.logoutButton.setOnClickListener {
            logout()
            requireActivity().startNewActivity(AuthActivity::class.java)
        }
    }

    private fun logout() {
        viewModel.logout()
    }
}