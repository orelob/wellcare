package com.nhathm.jobhunt.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.activity.*
import com.nhathm.jobhunt.adapter.loadImage
import com.nhathm.jobhunt.base.BaseFragment
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.databinding.FragmentMenuBinding
import com.nhathm.jobhunt.ui.user.UserViewModel
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment(
) : BaseFragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel by viewModels<MenuViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBinding.inflate(layoutInflater)

        arguments?.getBoolean("isRecruiter").let {
            binding.switchRole.text = if (it == true) "Switch to Job Seeker" else "Switch to Recruiter"
        }
        userViewModel.getUserAuth()
        userViewModel.userAuth.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    var user = it.value
                    user.avatarUrl?.let { it1 -> loadImage(binding.avatarUrl, it1) }
                    binding.fullName.text = user.fullName
                }
                is Resource.Failure -> {

                }
                is Resource.Loading -> {

                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)

//        binding.switchRole.text = if (isRecruiter) "Switch to Job Seeker" else "Switch to Recruiter"

        binding.logoutButton.setOnClickListener {
            logout()
        }

        binding.switchRole.setOnClickListener {
            switchRole()
        }

        binding.changePasswordButton.setOnClickListener {
            changePassword()
        }
    }

    fun changePassword() {
        binding.root.context.startActivity(
            Intent(
                binding.root.context,
                ChangePasswordActivity::class.java
            )
        )
    }

    fun logout() {
        viewModel.logout()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    fun switchRole() {
        if (binding.switchRole.text == "Switch to Recruiter") {

            userViewModel.getUserAuth()
            userViewModel.userAuth.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            var user = it.value
                            if (user.companyId == null) {
                                binding.root.context.startActivity(
                                    Intent(
                                        binding.root.context,
                                        CreateCompanyActivity::class.java
                                    ).putExtra("user", Gson().toJson(user))
                                )
                            } else {
                                binding.root.context.startActivity(
                                    Intent(
                                        binding.root.context,
                                        RecruiterActivity::class.java
                                    )
                                )
                            }
                        }
                    }
                    is Resource.Failure -> {
                        handleApiError(it) { }
                    }
                    else -> {}
                }
            })
        } else {
            requireActivity().startNewActivity(MainActivity::class.java)
        }
    }
}