package com.example.githubprofilesearch.repository

import com.example.githubprofilesearch.services.model.UserSearch
import com.example.githubprofilesearch.util.ResultOfNetwork

interface ISearchRepository {
    suspend fun doSearch(
        token: String,
        keyword: String,
        page: Int,
        count: Int
    ): ResultOfNetwork<UserSearch>
}