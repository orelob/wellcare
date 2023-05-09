package com.nhathm.wellcare.base

class BaseApiResponse {
    val code: Int = 0
    val message: String? = null
    val data: Any? = null
}

class BaseResponse<out T> {
    val code: Int = 0
    val message: String? = null
    val data: T? = null
}
