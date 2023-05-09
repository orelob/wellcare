package com.nhathm.wellcare.data.request;

data class SignUpRequest(
    val fullName: String? = null,
    val role: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null
)

data class SignInRequest(
    val email: String,
    val password: String
)
