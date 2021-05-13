package com.example.githubprofilesearch.repository

import com.example.githubprofilesearch.services.GithubServices
import com.example.githubprofilesearch.services.model.UserSearch
import com.example.githubprofilesearch.util.ResultOfNetwork
import java.lang.Exception
import javax.inject.Inject

class ProfileSearchRepository
@Inject
constructor( private val githubServices: GithubServices) : ISearchRepository{
    override suspend fun doSearch(
        token: String,
        keyword: String,
        page: Int,
        count: Int
    ): ResultOfNetwork<UserSearch> {
        return try{
            val response = githubServices.search(token, keyword, page, count)
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    return ResultOfNetwork.success(data)
                } ?: ResultOfNetwork.error("Request failed", null)
            } else {
                ResultOfNetwork.error("Request failed", null)
            }
        }catch (ex : Exception){
            ResultOfNetwork.error("Request failed ${ex.message}", null)
        }
    }
}