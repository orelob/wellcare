package com.nhathm.wellcare.ui.patient

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nhathm.jobhunt.ui.jobs.DoctorAdapter
import com.nhathm.jobhunt.ui.jobs.DoctorAdapter.DoctorAdapterCallback
import com.nhathm.wellcare.R
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Doctor
import com.nhathm.wellcare.databinding.FragmentDoctorListBinding
import com.nhathm.wellcare.ui.doctor.DoctorViewModel
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.view.adapter
import com.nhathm.wellcare.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SearchDoctorListFragment : Fragment() {

    private lateinit var binding: FragmentDoctorListBinding
    private val doctorViewModel: DoctorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorListBinding.inflate(layoutInflater)

        observer()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun observer() {
        doctorViewModel.doctorList.observe(viewLifecycleOwner, Observer { doctor ->
            binding.progressRefresh.visible(doctor is Resource.Loading)
            binding.doctorList.visible(doctor !is Resource.Loading)
            binding.noResult.visible(doctor !is Resource.Loading)
            binding.numberOfResult.visible(doctor !is Resource.Loading)

            when (doctor) {
                is Resource.Success -> {
                    lifecycleScope.launch {

                        val doctorAdapter = DoctorAdapter(doctor.value as MutableList<Doctor>)

                        binding.doctorList.adapter(
                            doctorAdapter,
                            1
                        )

                        doctorAdapter.setCallback(object : DoctorAdapterCallback {
                            override fun onDoctorSaved(position: Int, doctor: Doctor) {
                                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                                builder.setCancelable(true)

                                builder.setNegativeButton(
                                    "Cancel",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        dialog.dismiss()
                                    })

                                if (doctor.saved) {
                                    builder.setMessage("Do you want to unsave '" + doctor.fullName + "'?")
                                } else {
                                    builder.setMessage("Do you want to save '" + doctor.fullName + "'?")
                                }

                                builder.setPositiveButton(
                                    "Continue",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        doctorViewModel.saveDoctor(doctor)
                                        doctor.saved = !doctor.saved
                                        doctorAdapter.updateItem(position, doctor)
                                        dialog.dismiss()
                                    })

                                val dialog: AlertDialog = builder.create()
                                dialog.show()
                            }
                        })

                        binding.noResult.visible(doctor.value.isEmpty())
                        binding.doctorList.visible(doctor.value.isNotEmpty())
                        binding.numberOfResult.text = doctor.value.size.toString() + " found"
                    }
                }
                is Resource.Failure -> {
                    handleApiError(doctor)
                    binding.noResult.visible(false)
                }
                else -> {}
            }
        })
    }

}