package com.example.githubprofilesearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubprofilesearch.repository.ISearchRepository
import com.example.githubprofilesearch.services.model.UserSearch
import com.example.githubprofilesearch.util.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(
    private val repository: ISearchRepository,
    @Named("access_token") private val token: String
) : ViewModel() {
    private val mutableSearch = MutableLiveData<ResultOfNetwork<UserSearch>>()
    val searchResult: LiveData<ResultOfNetwork<UserSearch>> get() = mutableSearch

    /**
     * Search list of github user by keyword
     *
     * @param keyword search keyword
     * @param page results per page (max 100)
     * @param count page number of the results to fetch
     */
    fun getUserSearch(keyword: String, page: Int, count: Int) {
        viewModelScope.launch {
            mutableSearch.postValue(
                repository.doSearch(token, keyword, page, count)
            )
        }
    }
}