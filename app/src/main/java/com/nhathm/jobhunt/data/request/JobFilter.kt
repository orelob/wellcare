package com.nhathm.jobhunt.data.request

data class JobFilter(
    var title: String? = "",
    var minSalary: Int? = 0,
    var maxSalary: Int? = 2000000,
    var location: String? = "",
    var companySizes: List<String>,
    var jobTypes: List<String>,
)