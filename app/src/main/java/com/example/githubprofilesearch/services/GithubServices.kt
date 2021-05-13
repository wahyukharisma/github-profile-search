package com.example.githubprofilesearch.services

import com.example.githubprofilesearch.services.model.UserSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubServices {
    @GET("search/users")
    suspend fun search(
        @Header("Authorization") bearer: String,
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
    ): Response<UserSearch>
}