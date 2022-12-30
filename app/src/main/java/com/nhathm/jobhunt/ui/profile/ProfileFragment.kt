package com.nhathm.jobhunt.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.activity.EditProfileActivity
import com.nhathm.jobhunt.activity.JobDetailActivity
import com.nhathm.jobhunt.adapter.loadImage
import com.nhathm.jobhunt.base.BaseFragment
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.AppliedJob
import com.nhathm.jobhunt.data.model.*
import com.nhathm.jobhunt.databinding.FragmentAppliedBinding
import com.nhathm.jobhunt.databinding.FragmentProfileBinding
import com.nhathm.jobhunt.ui.auth.AuthViewModel
import com.nhathm.jobhunt.ui.jobs.JobsViewModel
import com.nhathm.jobhunt.ui.jobs.RecommendJobs
import com.nhathm.jobhunt.ui.user.UserViewModel
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.view.adapter
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<UserViewModel>()

    private lateinit var userC: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel.getUserAuth()
        setUp()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserAuth()
//        val educations = mutableListOf<Education>()
//        educations.add(Education("UET-VNU", "Bachelor of Computer Science", "2020 - 2024"))
//        educations.add(Education("HCMUS", "Computer Science", "2016 - 2020"))

//        binding.educationRecyclerView.adapter(EducationItem(educations), 1)

//        val experiences = mutableListOf<Experience>()
//        experiences.add(Experience(Company(1, "Google Inc", "", null), "Software Engineer"));
//        experiences.add(Experience(Company(1, "Google Inc", "", null), "Solution Architect"));
//        binding.experienceRecyclerView.adapter(ExperienceItem(experiences), 1)

        viewModel.userAuth.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        var user = it.value
                        it.value.avatarUrl?.let { it1 -> loadImage(binding.avatarUrl, it1) }
                        binding.fullName.text = user.fullName
                        binding.email.text = user.email
                        binding.bio.text = user.bio
                        binding.primRole.text = user.role
                        binding.address.text = user.location

                        userC = user
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {}
            }
        })
    }

    fun setUp() {
        binding.editProfileButton.setOnClickListener {
            binding.root.context.startActivity(
                Intent(
                    binding.root.context,
                    EditProfileActivity::class.java
                ).putExtra(Constant.USER, Gson().toJson(userC))
            )
        }
    }
}