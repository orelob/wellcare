package com.nhathm.wellcare.data

data class Patient(
    val name: String
)

data class Doctor(
    val id: String,
    val name: String
)

data class Appointment(
    val id: String
)
