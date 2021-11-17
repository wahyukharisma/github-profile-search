package com.example.githubprofilesearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubprofilesearch.repository.FakeProfileSearchRepository
import com.example.githubprofilesearch.services.model.UserSearch
import com.example.githubprofilesearch.util.JsonFileReader
import com.example.githubprofilesearch.util.Status
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class MainActivityViewModelTest{
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainActivityViewModel

    private val userSearch: UserSearch = Gson().fromJson(
        JsonFileReader("response_user_search.json").content,
        UserSearch::class.java
    )

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MainActivityViewModel(FakeProfileSearchRepository(),"access_token")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `search user, return success`() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.doSearch("user", 5, 1)
            }
        }
        viewModel.searchResult.observeForever { }
    }
}