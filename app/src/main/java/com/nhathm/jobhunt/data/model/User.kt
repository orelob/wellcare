package com.nhathm.jobhunt.data.model

data class User(
    var id: String,
    var fullName: String,
    var email: String,

    var bio: String? = null,
    var avatarUrl: String? = null,
    var location: String,

    var role: String? = null,
    var skills: List<Skill>? = null,

    var website: String? = "",
    var linkedIn: String? = "",
    var github: String? = "",
    var twitter: String? = "",

    var companyRole: String,
    var companyId: String? = null,
    var fcmToken: String? = null,
    var token: String? = null
)
