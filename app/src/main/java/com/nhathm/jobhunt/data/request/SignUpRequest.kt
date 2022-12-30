package com.nhathm.jobhunt.data.request

data class SignUpRequest(
    private val fullName: String,
    private val email: String,
    private val password: String
)
