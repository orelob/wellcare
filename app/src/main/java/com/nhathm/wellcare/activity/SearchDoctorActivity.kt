package com.nhathm.wellcare.activity

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.nhathm.wellcare.R
import com.nhathm.wellcare.adapter.PageAdapter
import com.nhathm.wellcare.databinding.ActivitySearchDoctorBinding
import com.nhathm.wellcare.databinding.FragmentPatientUpcomingAppointmentListBinding
import com.nhathm.wellcare.ui.doctor.DoctorViewModel
import com.nhathm.wellcare.ui.patient.PatientUpcomingAppointmentFragment
import com.nhathm.wellcare.ui.patient.SearchDoctorListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SearchDoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchDoctorBinding

    private val doctorViewModel by viewModels<DoctorViewModel>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchDoctorBinding.inflate(layoutInflater)

        val pageAdapter = PageAdapter(supportFragmentManager)
        listOf(
            "All",
            "Cardiologists",
            "Audiologists",
            "Dentists",
            "ENT specialists",
            "Gynecologists",
            "Orthopedic Surgeons"
        ).forEach { element ->
            pageAdapter.addWithTitle(SearchDoctorListFragment(), element)
        }

        binding.viewPager.adapter = pageAdapter
        binding.viewPager.currentItem = 0
        binding.categories.setViewPager(binding.viewPager)

        doctorViewModel.searchDoctor("", "")

        listener()
        observer()

        setContentView(binding.root)
    }

    fun listener() {
        binding.backButton.setOnClickListener {
            super.onBackPressed()
        }

        binding.categories.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                category = position.toString();
                doctorViewModel.searchDoctor(
                    binding.searchInput.text.toString(),
                    category.toString()
                );
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(1000)
                    doctorViewModel.searchDoctor(
                        binding.searchInput.text.toString(),
                        category.toString()
                    );
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    fun observer() {

    }

}