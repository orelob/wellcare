package com.codingwithme.firebasechat.`interface`

import com.nhathm.wellcare.data.NotificationData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FCMApi {

    @Headers("Authorization: key=AAAAGK9f7D8:APA91bFKFcyJ_sgUXVw42JDG-5HP254nufq4dMA2jp6O01JaqpAXuxlcdJuc1p3d_c0O2O9RhzFEmjHff6sQUYQmXumMeyPXrubVz17PXnglgdkp__k2gTX99UXDeXzxY3vkvCKPIcmu", "Content-type: application/json")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: Notification
    ): Response<ResponseBody>
}

data class Notification(
    val to: String,
    val data: NotificationData
)