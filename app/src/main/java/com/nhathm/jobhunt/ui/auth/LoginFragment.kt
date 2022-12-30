package com.nhathm.jobhunt.ui.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.TokenViewModel
import com.nhathm.jobhunt.activity.AuthActivity
import com.nhathm.jobhunt.activity.MainActivity
import com.nhathm.jobhunt.base.BaseFragment
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.LocalDataStore
import com.nhathm.jobhunt.databinding.FragmentLoginBinding
import com.nhathm.jobhunt.utils.enable
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.startNewActivity
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    private val tokenViewModel: TokenViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp() {
        binding.progressbar.visible(false)
        binding.loginButton.enable(false)

        binding.loginButton.setOnClickListener {
            login()
        }

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                Log.i("SAVED_TOKEN_LOCAL", token)
            }
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val loginResponse = it.value

//                        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
//                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                        editor.putString("ACCESS_TOKEN", loginResponse.token)
//                        editor.apply()

                        tokenViewModel.saveToken(loginResponse.token)

                        viewModel.saveAccessToken(
                            loginResponse.token
                        )
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w(Constants.TAG, "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }
                            val token = task.result
                            Log.i("FCM-TOKEN", token);
                                lifecycleScope.launch {
                                    viewModel.saveFcmTokenForUser(
                                        loginResponse.user.id, token
                                    )
                                }

                        })
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it) { login() }
                }
                else -> {}
            }
        })



        binding.passwordInput.addTextChangedListener {
            val email = binding.emailInput.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.signUpText.setOnClickListener {
            val viewPager = (activity as AuthActivity).findViewById<ViewPager>(R.id.viewPager)
            viewPager.currentItem = 1
        }
    }

    private fun login() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        viewModel.login(email, password)
    }
}

