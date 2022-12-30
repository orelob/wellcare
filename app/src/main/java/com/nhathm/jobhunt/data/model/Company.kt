package com.nhathm.jobhunt.data.model

data class Company(
    var id: String,
    var name: String,
    var logoUrl: String,
    var website: String,
    var overview: String,
    var location: String,
    var markets: List<String>,
    var companySize: String,
)