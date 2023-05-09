package com.nhathm.wellcare.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.nhathm.wellcare.R
import com.nhathm.wellcare.activity.AuthActivity
import com.nhathm.wellcare.base.BaseApiResponse
import com.nhathm.wellcare.base.BaseFragment
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.request.SignUpRequest
import com.nhathm.wellcare.databinding.FragmentSignUpBinding
import com.nhathm.wellcare.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<AuthViewModel>()

    private var role = "PATIENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp() {
        binding.progressbar.visible(false)
        binding.signUpButton.enable(true)

        binding.signUpButton.setOnClickListener {
            signUp()
        }

        binding.signInText.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.radioRoles.setOnCheckedChangeListener { radioGroup, optionId ->
            run {
                when (optionId) {
                    R.id.role_doctor_radio -> {
                        role = "DOCTOR"
                    }
                    R.id.role_patient_radio -> {
                        role = "PATIENT"
                    }
                }
                requireView().snackbar(role)
            }
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val viewPager =
                            (activity as AuthActivity).findViewById<ViewPager>(R.id.viewPager)
                        viewPager.currentItem = 0

                        val response = it.value

                        response.message?.let { it1 -> requireView().snackbar(it1) }
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it) { signUp() }
                }
                else -> {}
            }
        })
    }

    private fun signUp() {
        val fullName = binding.fullName.text.toString().trim()
        val role = role
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val confirmPassword = binding.confirmPassword.text.toString().trim()

        viewModel.signup(SignUpRequest(fullName, role, email, password, confirmPassword))
    }
}

