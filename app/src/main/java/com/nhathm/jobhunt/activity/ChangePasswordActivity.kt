package com.nhathm.jobhunt.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.base.Resource
import com.nhathm.jobhunt.data.model.User
import com.nhathm.jobhunt.databinding.ActivityChangePasswordBinding
import com.nhathm.jobhunt.databinding.ActivityCreateCompanyBinding
import com.nhathm.jobhunt.ui.auth.AuthViewModel
import com.nhathm.jobhunt.ui.company.CompanyViewModel
import com.nhathm.jobhunt.utils.handleApiError
import com.nhathm.jobhunt.utils.startNewActivity
import com.nhathm.jobhunt.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityChangePasswordBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun setUp() {
        binding.backButton.setOnClickListener { onBackPressed() }
        binding.changePasswordButton.setOnClickListener {
            changePassword()
        }

        authViewModel.changePasswordResponse.observe(this, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        val changePasswordResponse = it.value

                        Toast.makeText(
                            this@ChangePasswordActivity,
                            changePasswordResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Invalid request. Please check your input",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        })
    }

    fun changePassword() {
        authViewModel.changePassword(
            binding.currentPassword.text.toString(),
            binding.newPassword.text.toString(),
            binding.confirmPassword.text.toString()
        )
    }
}