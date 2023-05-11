package com.nhathm.wellcare.ui.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.gzuliyujiang.wheelpicker.TimePicker
import com.github.gzuliyujiang.wheelpicker.annotation.TimeMode
import com.github.gzuliyujiang.wheelpicker.contract.TimeFormatter
import com.github.gzuliyujiang.wheelpicker.entity.TimeEntity
import com.github.gzuliyujiang.wheelpicker.impl.UnitTimeFormatter
import com.github.gzuliyujiang.wheelpicker.widget.TimeWheelLayout
import com.nhathm.wellcare.databinding.FragmentBookAppointmentBinding
import com.nhathm.wellcare.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientBookAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentBookAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookAppointmentBinding.inflate(inflater)

        binding.calendar.minDate = System.currentTimeMillis()

        binding.hourPicker.setOnClickListener {
            openHourPicker()
        }

        return binding.root
    }

    private fun openHourPicker() {
        val picker = TimePicker(requireActivity())
        val wheelLayout: TimeWheelLayout = picker.wheelLayout
        wheelLayout.setTimeMode(TimeMode.HOUR_12_NO_SECOND)
        wheelLayout.setTimeFormatter(object : TimeFormatter {
            override fun formatHour(hour: Int): String {
                return hour.toString()
            }

            override fun formatMinute(minute: Int): String {
                return minute.toString()
            }

            override fun formatSecond(second: Int): String {
                return second.toString()
            }
        })
        wheelLayout.setDefaultValue(TimeEntity.now())
        wheelLayout.setResetWhenLinkage(false)
        picker.show()
    }
}