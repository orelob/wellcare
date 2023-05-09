package com.nhathm.wellcare.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhathm.wellcare.R
import com.nhathm.wellcare.base.BaseApiResponse
import com.nhathm.wellcare.base.Resource
import com.nhathm.wellcare.ui.auth.SignInFragment


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }

    snackbar.setAnchorView(findViewById(R.id.bottom_navigation))
    snackbar.show()
}

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun Context.getCompatActivity(): AppCompatActivity? {
    return when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> baseContext.getCompatActivity()
        else -> null
    }
}

fun TextView.html(foo: String) {
    text = HtmlCompat.fromHtml(foo, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is SignInFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                requireView().snackbar("Something wnent wrong, please try again later")
            }
        }
        else -> {
            val errorObj = failure.errorBody?.string().toString()
            Log.e("shi3k-error", errorObj)
            Gson().fromJson(errorObj, BaseApiResponse::class.java).let {
                it.message?.let { it1 -> requireView().snackbar(it1) }
            }
        }
    }
}

fun <T> castToObject(str: BaseApiResponse, clazz: Class<T>): T {
    return Gson().fromJson(str.data.toString(), clazz)
}

fun <T> castToList(str: BaseApiResponse, clazz: Class<T>): List<T> {
    val gson = Gson()
    val type = TypeToken.getParameterized(List::class.java, clazz).type
    return gson.fromJson(str.data.toString(), type)
}

fun <T> LiveData<T>.asResource(): LiveData<Resource<T>> {
    return Transformations.map(this) { data ->
        Resource.Success(data)
    }
}