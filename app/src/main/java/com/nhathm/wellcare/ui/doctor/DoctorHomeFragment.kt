package com.nhathm.wellcare.ui.doctor

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nhathm.wellcare.R as myR
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.databinding.FragmentPatientHomeBinding
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.view.adapter
import com.nhathm.wellcare.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DoctorHomeFragment : Fragment() {

    private lateinit var binding: FragmentPatientHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientHomeBinding.inflate(layoutInflater)

        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.expandableLayout.visible(false)
        binding.filterButton.setOnClickListener {
            if (binding.expandableLayout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.expandableLayout.visible(true)
                binding.filterButton.setImageResource(myR.drawable.ic_baseline_filter_list_active_24)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.expandableLayout.visible(false)
                binding.filterButton.setImageResource(myR.drawable.ic_baseline_filter_list_24)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.jobList.observe(viewLifecycleOwner, Observer {
//            binding.progressbar.visible(it is Resource.Loading)
//            when (it) {
//                is Resource.Success -> {
//                    lifecycleScope.launch {
//
//                    }
//                }
//                is Resource.Failure -> {
//                    handleApiError(it)
//                }
//                else -> {}
//            }
//        })
    }

}