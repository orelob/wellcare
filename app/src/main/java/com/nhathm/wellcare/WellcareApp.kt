package com.nhathm.wellcare

import android.app.Application
import com.nhathm.wellcare.data.SharedPreferencesManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WellcareApp: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(applicationContext)
    }
}