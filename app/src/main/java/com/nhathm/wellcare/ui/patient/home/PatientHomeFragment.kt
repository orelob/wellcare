package com.nhathm.wellcare.ui.patient.home

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.nhathm.jobhunt.ui.jobs.ArticleAdapter
import com.nhathm.jobhunt.ui.jobs.TopDoctorAdapter
import com.nhathm.jobhunt.ui.jobs.UpcomingAppointmentAdapter
import com.nhathm.wellcare.activity.BookAppointmentActivity
import com.nhathm.wellcare.activity.DoctorDetailActivity
import com.nhathm.wellcare.activity.OnBoardingActivity
import com.nhathm.wellcare.activity.SearchDoctorActivity
import com.nhathm.wellcare.adapter.loadImage
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.Article
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.FragmentPatientHomeBinding
import com.nhathm.wellcare.ui.auth.AuthViewModel
import com.nhathm.wellcare.ui.items.TopDoctorListClickListener
import com.nhathm.wellcare.ui.patient.PatientViewModel
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.snackbar
import com.nhathm.wellcare.utils.view.adapter
import com.nhathm.wellcare.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class PatientHomeFragment : Fragment(), TopDoctorListClickListener {

    private lateinit var binding: FragmentPatientHomeBinding

    private val patientViewModel by viewModels<PatientViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    private val articleViewModel by viewModels<ArticlePatientHomeViewModel>()

    lateinit var listener: TopDoctorListClickListener
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientHomeBinding.inflate(layoutInflater)

        coroutineScope.launch(Dispatchers.Main) {
            fetchData()
        }

        listener()

        binding.progressRefresh.visible(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.currentUser.observe(viewLifecycleOwner, Observer { user ->
//            binding.articleProgressbar.visible(articles is Resource.Loading)
            when (user) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        loadImage(binding.avatar, user.value.avatar)
                    }
                }
                is Resource.Failure -> {
                    handleApiError(user)
                }
                else -> {}
            }
        })

        articleViewModel.articleList.observe(viewLifecycleOwner, Observer { articles ->
//            binding.articleProgressbar.visible(articles is Resource.Loading)
            when (articles) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.articles.adapter(
                            ArticleAdapter(articles.value as MutableList<Article>)
                        )
                    }
                }
                is Resource.Failure -> {
                    handleApiError(articles)
                }
                else -> {}
            }
        })


        patientViewModel.upcomingAppointmentList.observe(
            viewLifecycleOwner,
            Observer { appointments ->
//            binding.progressbar.visible(appointments is Resource.Loading)
                when (appointments) {
                    is Resource.Success -> {
                        lifecycleScope.launch {

                            binding.upcomingAppointments.adapter(
                                UpcomingAppointmentAdapter(appointments.value as MutableList<Appointment>),
                                1
                            )
                        }
                    }
                    is Resource.Failure -> {
                        handleApiError(appointments)
                    }
                    else -> {}
                }
            })

        listener = this


        patientViewModel.topDoctorList.observe(viewLifecycleOwner, Observer { doctors ->
//            binding.progressbar.visible(doctors is Resource.Loading)
            when (doctors) {
                is Resource.Success -> {
                    lifecycleScope.launch {


                        binding.topDoctors.adapter(
                            TopDoctorAdapter(doctors.value as MutableList<Doctor>, listener),
                            false, false
                        )
                    }
                }
                is Resource.Failure -> {
                    handleApiError(doctors)
                }
                else -> {}
            }
        })

    }

    override fun onDoctorListItemClick(view: View, doctor: Doctor) {

        binding.root.context.startActivity(
            Intent(
                binding.root.context,
                DoctorDetailActivity::class.java
            )
        )
    }

    fun listener() {
        binding.appBarLayout.setOnClickListener {
            if (binding.progressRefresh.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.progressRefresh.visible(true)

                coroutineScope.launch {
                    fetchData()
                    binding.progressRefresh.visible(false)
                }

            }
        }

        binding.searchInput.setOnClickListener {
            requireActivity().startActivity(Intent(activity, SearchDoctorActivity::class.java))
        }
    }

    private suspend fun fetchData() {
        delay(2000)
        authViewModel.getCurrentUser()
        articleViewModel.getArticles()
        patientViewModel.getUpcomingAppointments()
        patientViewModel.getTopDoctors()
        delay(2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}