package com.nhathm.jobhunt.data.model

data class JobDto(
    var job: Job,
    var company: Company,
    var hiringContact: User,
    var applied: Boolean,
    var saved: Boolean
)

data class Job(
    var id: String,
    var title: String,
    var description: String,
    var jobType: String,
    var companyId: String,
    var hiringContactId: String,
    var location: String,
    var minSalary: Int,
    var maxSalary: Int,
    var workType: String,
    var yearsOfExperience: String,
    var skills: List<String>,

    var createdAt: String,
    var updatedAt: String
)