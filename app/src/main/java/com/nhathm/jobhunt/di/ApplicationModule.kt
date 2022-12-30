package com.nhathm.jobhunt.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.codingwithme.firebasechat.`interface`.FCMApi
import com.nhathm.jobhunt.AuthAuthenticator
import com.nhathm.jobhunt.AuthInterceptor
import com.nhathm.jobhunt.TokenService
import com.nhathm.jobhunt.data.api.AuthApi
import com.nhathm.jobhunt.data.api.CompanyApi
import com.nhathm.jobhunt.data.api.JobApi
import com.nhathm.jobhunt.data.api.UserApi
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

    //    val BASE_URL = "http://10.0.2.2:8000/api/";
    val BASE_URL = "http://192.168.137.1:8000/api/";
//    val BASE_URL = "http://192.168.137.1:8000/api/";

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenService =
        TokenService(context)

//    @Singleton
//    @Provides
//    fun retrofit(
//        authInterceptor: AuthInterceptor,
//        authAuthenticator: AuthAuthenticator,
//    ): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        return OkHttpClient.Builder()
//            .addInterceptor(authInterceptor)
//            .addInterceptor(loggingInterceptor)
//            .authenticator(authAuthenticator)
//            .build()
//    }


    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenService): AuthInterceptor =
        AuthInterceptor(tokenManager)

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
    fun provideRetrofit(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun proviodeJob(retrofit: Retrofit): JobApi = retrofit.create(JobApi::class.java)

    @Provides
    @Singleton
    fun provideCompany(retrofit: Retrofit): CompanyApi = retrofit.create(CompanyApi::class.java)

}