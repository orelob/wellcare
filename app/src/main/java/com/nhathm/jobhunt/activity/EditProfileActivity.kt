package com.nhathm.jobhunt.activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.nhathm.jobhunt.Constant
import com.nhathm.jobhunt.R
import com.nhathm.jobhunt.data.model.JobDto
import com.nhathm.jobhunt.data.model.User
import com.nhathm.jobhunt.data.request.CreateJobRequest
import com.nhathm.jobhunt.databinding.ActivityEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart
import java.io.ByteArrayOutputStream
import java.io.File


@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    lateinit var avatarUri: Uri


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        avatarUri = it!!
        binding.avatar.setImageURI(it)

        uploadAvatar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Gson().fromJson(intent.getStringExtra(Constant.USER), User::class.java)

        binding.fullName.setText(user.fullName)
        binding.location.setText(user.location)
        binding.bio.setText(user.bio)
        binding.linkedIn.setText(user.linkedIn)
        binding.twitter.setText(user.twitter)
        binding.website.setText(user.website)
        binding.avatar.setImageURI(Uri.parse(user.avatarUrl))
        binding.github.setText(user.github)

        setUp()
    }

    fun setUp() {
        binding.avatar.setOnClickListener {
            contract.launch("image/*")
        }
    }

    fun uploadAvatar() {
        Log.i("Start upload", "Start upload")
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
                Toast.makeText(this, "Upload success", Toast.LENGTH_LONG).show()
                Log.i("Upload success", downloadUri.toString())
                binding.avatar.setImageURI(downloadUri)
            } else {
                Toast.makeText(this, "Upload failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateUser() {
        val user = Gson().fromJson(intent.getStringExtra(Constant.USER), User::class.java)

        user.fullName = binding.fullName.text.toString()
        user.location = binding.location.text.toString()
        user.bio = binding.bio.text.toString()
        user.linkedIn = binding.linkedIn.text.toString()
        user.twitter = binding.twitter.text.toString()
        user.website = binding.website.text.toString()
        user.github = binding.github.text.toString()
        
    }
}