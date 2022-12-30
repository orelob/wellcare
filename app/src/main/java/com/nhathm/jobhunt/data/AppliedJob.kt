package com.nhathm.jobhunt.data

import com.nhathm.jobhunt.data.model.Job

data class AppliedJob(
    var job: Job,
    var status: String,
    var appliedDate: String,
    var note: String?
)
