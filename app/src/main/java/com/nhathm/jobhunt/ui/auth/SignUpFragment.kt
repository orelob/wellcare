package com.nhathm.jobhunt.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.activity.AuthActivity
import com.nhathm.jobhunt.activity.MainActivity
import com.nhathm.jobhunt.base.BaseFragment
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.databinding.FragmentLoginBinding
import com.nhathm.jobhunt.databinding.FragmentSignUpBinding
import com.nhathm.jobhunt.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<AuthViewModel>()

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
        binding.signUpButton.enable(false)

        binding.signUpButton.setOnClickListener {
            signUp()
        }

        binding.loginText.setOnClickListener {
            val viewPager = (activity as AuthActivity).findViewById<ViewPager>(R.id.viewPager)
            viewPager.currentItem = 0
        }

        binding.password.addTextChangedListener {
            val email = binding.email.text.toString().trim()
            binding.signUpButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val viewPager = (activity as AuthActivity).findViewById<ViewPager>(R.id.viewPager)
                        viewPager.currentItem = 0

                        requireView().snackbar("Sign up was success. Please login to continue.")
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
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        viewModel.signup(fullName, email, password)
    }
}

