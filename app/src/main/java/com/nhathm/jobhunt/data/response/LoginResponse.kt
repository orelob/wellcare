package com.nhathm.jobhunt.data.response

import com.nhathm.jobhunt.data.model.User

data class LoginResponse(
    val token: String,
    var user: User
)

data class LoginResponseObj(
    val token: String,
    val refreshToken: String,
    val id: String,
    val fullName: String,
    val email: String,
)