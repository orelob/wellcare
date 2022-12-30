package com.nhathm.jobhunt.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nhathm.jobhunt.activity.AuthActivity
import com.nhathm.jobhunt.activity.OnBoardingActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "local_data_store")

class LocalDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val appContext = context.applicationContext

    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    suspend fun saveAccessToken(accessToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun saveFcmToken(fcmToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[FCM_TOKEN] = fcmToken
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val FCM_TOKEN = stringPreferencesKey("fcm_token")
    }
}