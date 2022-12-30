package com.nhathm.jobhunt.data.request

data class CreateJobRequest(
    var title: String,
    var description: String,
    var jobType: String,
    var location: String,
    var minSalary: Int,
    var maxSalary: Int,
    var workType: String,
    var yearsOfExperience: String,
    var skills: String,
)
