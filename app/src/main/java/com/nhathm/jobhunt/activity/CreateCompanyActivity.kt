package com.nhathm.jobhunt.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.nhathm.jobhunt.data.model.User
import com.nhathm.jobhunt.data.request.CreateCompanyRequest
import com.nhathm.jobhunt.databinding.ActivityCreateCompanyBinding
import com.nhathm.jobhunt.ui.company.CompanyViewModel
import com.nhathm.jobhunt.utils.enable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateCompanyActivity @Inject constructor(
) : AppCompatActivity() {
    lateinit var avatarUri: Uri
    lateinit var binding: ActivityCreateCompanyBinding
    lateinit var companySize: String

    private val companyViewModel by viewModels<CompanyViewModel>()

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        avatarUri = it!!
        binding.companyLogo.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    fun setUp() {
        binding.backButton.setOnClickListener { onBackPressed() }

        companySize = "1-10 employees"
        binding.companyLogo.setOnClickListener {
            contract.launch("image/*")
        }
        binding.radio1.setOnClickListener {
            companySize = "1-10 employees"
        }
        binding.radio2.setOnClickListener {
            companySize = "11-50 employees"
        }
        binding.radio3.setOnClickListener {
            companySize = "51-200 employees"
        }
        binding.radio4.setOnClickListener {
            companySize = "201-500 employees"
        }
        binding.radio5.setOnClickListener {
            companySize = "501+ employees"
        }

        binding.createCompanyButton.setOnClickListener {
            createCompany()
        }
    }

    fun createCompany() {
        Log.i("Start upload", "Start upload")
        binding.progressBar.enable(true)

        try {
            val storageRef = FirebaseStorage.getInstance().getReference("images/test/" + System.currentTimeMillis())
            val uploadTask = storageRef.putFile(avatarUri)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Toast.makeText(this, "Upload logo success", Toast.LENGTH_LONG).show()
                    Log.i("Upload logo success", downloadUri.toString())
                    binding.logoURL.text = downloadUri.toString()
                } else {
                    Toast.makeText(this, "Upload logo failed", Toast.LENGTH_LONG).show()
                }
                val user = Gson().fromJson(intent.getStringExtra("user"), User::class.java)
                var request = CreateCompanyRequest(
                    user.id, binding.companyRoleOfUser.text.toString(), binding.name.text.toString(),
                    binding.logoURL.text.toString(),
                    binding.website.text.toString(),
                    binding.description.text.toString(),
                    binding.location.text.toString(),
                    binding.markets.text.toString().split(",").map { it.trim() },
                    companySize
                )
                companyViewModel.createCompany(request)
                binding.progressBar.enable(false)
                binding.root.context.startActivity(
                    Intent(
                        binding.root.context,
                        RecruiterActivity::class.java
                    ).putExtra("user", Gson().toJson(user))
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Some thing went wrong when create Company", e.message.toString())
            Toast.makeText(this, "Some thing went wrong when create Company", Toast.LENGTH_LONG).show()
            binding.progressBar.enable(false)
        }
    }
}