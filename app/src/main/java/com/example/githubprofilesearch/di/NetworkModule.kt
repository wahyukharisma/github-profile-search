package com.example.githubprofilesearch.di

import com.example.githubprofilesearch.services.GithubServices
import com.example.githubprofilesearch.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val client : OkHttpClient = OkHttpClient.Builder()
        .build()

    @Singleton
    @Provides
    @Named("access_token")
    fun provideAccessToken() : String{
        return "Bearer ${BuildConfig.API_KEY}"
    }

    @Singleton
    @Provides
    fun provideServices() : GithubServices{
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GithubServices::class.java)
    }
}