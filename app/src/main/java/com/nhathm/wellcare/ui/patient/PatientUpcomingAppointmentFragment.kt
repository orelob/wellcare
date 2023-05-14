package com.nhathm.wellcare.ui.patient

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nhathm.jobhunt.ui.jobs.UpcomingAppointmentAdapter
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.databinding.FragmentPatientUpcomingAppointmentListBinding
import com.nhathm.wellcare.utils.handleApiError
import com.nhathm.wellcare.utils.view.adapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class PatientUpcomingAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentPatientUpcomingAppointmentListBinding
    private val viewModel by viewModels<PatientViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientUpcomingAppointmentListBinding.inflate(layoutInflater)

//        binding.calendar.minDate = System.currentTimeMillis()
//        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            val calendar = Calendar.getInstance()
//            calendar.set(year, month, dayOfMonth)
//            val selectedDate = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
//
//            binding.textView.setText(selectedDate)
//        }

        binding.noResult.setOnClickListener { showDatePickerDialog() }

        observe()

        return binding.root
    }

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    private fun showDatePickerDialog() {
        // Get the current date
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and set the current date as the default date
        val datePickerDialog = DatePickerDialog(
            this.requireContext(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // Store the selected date
                selectedYear = year
                selectedMonth = monthOfYear
                selectedDay = dayOfMonth

                // Update your UI or perform any required actions with the selected date
                updateSelectedDate()
            },
            year,
            month,
            day
        )
        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    private fun updateSelectedDate() {
        // Convert the month value to a human-readable format (since the month value is zero-based)
        val months = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        val selectedDate = months[selectedMonth] + " " + selectedDay + ", " + selectedYear

        // Update your UI or perform any required actions with the selected date
//        binding.textView.setText(selectedDate)
    }

    fun observe() {
        viewModel.upcomingAppointmentList.observe(viewLifecycleOwner, Observer { appointments ->
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
    }

}