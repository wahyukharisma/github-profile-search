package com.example.githubprofilesearch.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import com.example.githubprofilesearch.databinding.ActivityMainBinding
import com.example.githubprofilesearch.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModelMain: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var page = 1
    private var keyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            etSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    && etSearch.text.isNotEmpty()
                ) {
                    keyword = etSearch.text.toString()
                    viewModelMain.doSearch(keyword, page, 5)
                    true
                } else {
                    etSearch.error = "Insert keyword"
                    etSearch.requestFocus()
                    false
                }
            }

            viewModelMain.searchResult.observe(this@MainActivity, { result ->
                if (result.status == Status.SUCCESS) {
                    result.data?.let { data ->
                        if (data.totalCount > 0) {
                            // Adding to list
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Profile not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, result.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}