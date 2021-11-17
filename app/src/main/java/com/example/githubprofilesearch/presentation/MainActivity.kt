package com.example.githubprofilesearch.presentation

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprofilesearch.databinding.ActivityMainBinding
import com.example.githubprofilesearch.presentation.adapter.SearchResultAdapter
import com.example.githubprofilesearch.services.model.UserSearch
import com.example.githubprofilesearch.util.Status
import com.example.githubprofilesearch.util.hide
import com.example.githubprofilesearch.util.remove
import com.example.githubprofilesearch.util.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModelMain: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchResultAdapter

    private var page = 1
    private var keyword = ""
    private var testing = ""

    companion object {
        const val COUNT = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeAdapter()

        with(binding) {
            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    && etSearch.text.isNotEmpty()
                ) {
                    page = 1
                    keyword = etSearch.text.toString()
                    viewModelMain.doSearch(keyword, page, COUNT)
                    etSearch.hideKeyboard()
                } else {
                    etSearch.error = "Insert keyword"
                    etSearch.requestFocus()
                }
                true
            }

            rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        page += 1
                        viewModelMain.doSearch(keyword, page, COUNT)
                        binding.tvLoadMore.show()
                    }
                }
            })

            viewModelObserve()
        }
    }

    private fun doPopulateSearchData(
        data: UserSearch,
        page: Int,
        adapter: SearchResultAdapter
    ) {
        if (data.totalCount > 0) {
            if (page == 1) {
                binding.tvEmptyProfile.remove()
                adapter.removeAllData()
            }
            binding.tvLoadMore.hide()
            adapter.populateData(data.items)
        } else {
            Toast.makeText(
                this@MainActivity,
                "Profile not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun viewModelObserve() {
        viewModelMain.searchResult.observe(this@MainActivity, { result ->
            if (result.status == Status.SUCCESS) {
                result.data?.let { data ->
                    doPopulateSearchData(data, page, adapter)
                }
            } else {
                Toast.makeText(this@MainActivity, result.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initializeAdapter() {
        val layoutManager = LinearLayoutManager(this)
        adapter = SearchResultAdapter()

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvContent.context,
            layoutManager.orientation
        )

        binding.rvContent.adapter = adapter
        binding.rvContent.addItemDecoration(dividerItemDecoration)
    }

    private fun EditText.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}