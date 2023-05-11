package com.nhathm.wellcare.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.codingwithme.firebasechat.`interface`.FCMApi
import com.nhathm.wellcare.AuthInterceptor
import com.nhathm.wellcare.TokenService
import com.nhathm.wellcare.data.Appointment
import com.nhathm.wellcare.data.api.AppointmentApi
import com.nhathm.wellcare.data.api.AuthApi
import com.nhathm.wellcare.data.api.DoctorApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

//        val BASE_URL = "http://10.225.89.241:8001/api/";
//        val BASE_URL = "http://10.213.121.241:8001/api/";
        val BASE_URL = "http://192.168.137.1:8001/api/";

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenService = TokenService(context)


    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
    fun retrofit(authenticator: AuthInterceptor): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor(authenticator)
                .build()
        ).build()


    @Provides
    @Singleton
    fun provideFCM(): FCMApi =
        Retrofit.Builder().baseUrl("https://fcm.googleapis.com").build().create(FCMApi::class.java)

    @Provides
    @Singleton
    fun provideAuth(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAppointment(retrofit: Retrofit): AppointmentApi = retrofit.create(AppointmentApi::class.java)

    @Provides
    @Singleton
    fun provideDoctor(retrofit: Retrofit): DoctorApi = retrofit.create(DoctorApi::class.java)
}