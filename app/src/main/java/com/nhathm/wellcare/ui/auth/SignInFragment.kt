package com.nhathm.wellcare.ui.auth

import android.os.Bundle
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
import com.nhathm.wellcare.databinding.FragmentSignInBinding
import com.nhathm.wellcare.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.progressbar.visible(false)
        binding.signInButton.enable(false)

        binding.signInButton.setOnClickListener {
            signIn()
        }


        viewModel.signInResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {

                        val signInResponse = it.value
                        SharedPreferencesManager.setAuthToken(signInResponse.token)
                        SharedPreferencesManager.setRole(signInResponse.role)

                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w(
                                        Constants.TAG,
                                        "Fetching FCM registration token failed",
                                        task.exception
                                    )
                                    return@OnCompleteListener
                                }
                                val token = task.result

                                lifecycleScope.launch {
//                                    viewModel.saveFcmTokenForUser(token)
                                }

                            })
                        render()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it) { signIn() }
                }
                else -> {}
            }
        })

        binding.passwordInput.addTextChangedListener {
            val email = binding.emailInput.text.toString().trim()
            binding.signInButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }



        binding.signUpText.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.forgotPasswordText.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }
    }

    private fun signIn() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        viewModel.signIn(email, password)
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

        requireActivity().startNewActivity(activity)
    }
}

