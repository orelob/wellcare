package com.nhathm.jobhunt.data.request

data class CreateCompanyRequest(
    var userId: String,
    var companyRoleOfUser: String,

    var name: String,
    var logoUrl: String?,
    var website: String,
    var description: String,
    var location: String,
    var markets: List<String>,
    var companySize: String,
)