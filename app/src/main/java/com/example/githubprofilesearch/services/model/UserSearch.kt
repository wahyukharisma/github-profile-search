package com.example.githubprofilesearch.services.model

import com.google.gson.annotations.SerializedName

data class UserSearch(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)