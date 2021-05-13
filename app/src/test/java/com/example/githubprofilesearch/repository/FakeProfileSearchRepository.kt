package com.example.githubprofilesearch.repository

import com.example.githubprofilesearch.services.model.UserSearch
import com.example.githubprofilesearch.util.JsonFileReader
import com.example.githubprofilesearch.util.ResultOfNetwork
import com.google.gson.Gson

class FakeProfileSearchRepository : ISearchRepository {
    private val userSearch : UserSearch = Gson().fromJson(JsonFileReader("response_user_search.json").content, UserSearch::class.java)

    private var testingFailure = false

    override suspend fun doSearch(
        token: String,
        keyword: String,
        page: Int,
        count: Int
    ): ResultOfNetwork<UserSearch> {
        return if(testingFailure){
            ResultOfNetwork.error("Request Failed", null)
        }else{
            ResultOfNetwork.success(userSearch)
        }
    }
}