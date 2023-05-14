package com.nhathm.wellcare.data

data class Patient(
    val name: String
)

data class Doctor(
    val id: String,
    val fullName: String,
    var saved: Boolean
)

data class Appointment(
    val id: String
)

data class Article(
    val id: String? = null,
    val title: String,
    val thumbnail: String,
    val description: String,
    val createdAt: String? = null
)

data class JwtResponse(
    val token: String,
    val id: String,
    val fullName: String,
    val email: String,
)

data class User(
    val email: String,
    val avatar: String,
    val fullName: String
)