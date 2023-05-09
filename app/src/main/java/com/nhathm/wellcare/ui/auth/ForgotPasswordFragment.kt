package com.nhathm.wellcare.ui.auth

import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
import com.nhathm.wellcare.R
import com.nhathm.wellcare.TokenViewModel
import com.nhathm.wellcare.activity.AuthActivity
import com.nhathm.wellcare.activity.DoctorActivity
import com.nhathm.wellcare.activity.PatientActivity
import com.nhathm.wellcare.base.BaseFragment
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.SharedPreferencesManager
import com.nhathm.wellcare.data.response.SignInResponse
import com.nhathm.wellcare.databinding.FragmentForgotPasswordBinding
import com.nhathm.wellcare.databinding.FragmentSignInBinding
import com.nhathm.wellcare.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        with(binding) {
            sendOTPButton.setOnClickListener {
                view?.findNavController()?.navigate(R.id.action_forgotPasswordFragment_to_otpVerificationFragment)
            }
            backButton.setOnClickListener {
                view?.findNavController()?.navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
            }

        }
    }
}

