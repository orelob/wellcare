package com.nhathm.wellcare.data.response

data class SignInResponse(
    val id: String,
    val fullName: String,
    val email: String,
    val token: String,
    val role: String,
)

