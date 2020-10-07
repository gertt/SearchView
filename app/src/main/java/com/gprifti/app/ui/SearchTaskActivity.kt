package com.gprifti.app.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gprifti.app.R
import com.gprifti.app.databinding.ActivitySearchBinding
import com.gprifti.app.db.TaskDatabase
import com.gprifti.app.repository.SearchRepository
import com.gprifti.app.util.Constants.Companion.SEARCH_TIME_DELAY
import kotlinx.coroutines.*


class SearchTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchTaskViewModel
    private lateinit var adapter: SearchTaskAdapter

    private var searchJob: Job? = null
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observeFilterType()
        observeViewState()
        setUpRecycleView()
        bindSearchView()

        // bind the search view model
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        val searchRepository = SearchRepository(TaskDatabase(this))
        val viewModelProviderFactory =
            SearchTaskViewModelProviderFactory(application, searchRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SearchTaskViewModel::class.java)
    }

    /** when filter type change, select the filter type and unselected the other */
    private fun observeFilterType() {
        viewModel.filterType.observe(this, Observer{ btnState ->
            if (btnState == SearchTaskViewModel.FilterType.ALL.filter) {
                binding.btnAll.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.button_fllter_selected
                )
            } else {
                binding.btnAll.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.button_filter_not_selected
                )
            }

            if (btnState == SearchTaskViewModel.FilterType.ACTIVE.filter) {
                binding.btnActive.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.button_fllter_selected
                )
            } else {
                binding.btnActive.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.button_filter_not_selected
                )
            }

            if (btnState == SearchTaskViewModel.FilterType.ARCHIVED.filter) {
                binding.btnArchive.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.button_fllter_selected
                )
            } else {
                binding.btnArchive.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.button_filter_not_selected
                )
            }
        })
    }

    /**  when the viewState changes, also we change what we show into the view, loading, result or init screen */
    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer{ searchViewState ->
            when (searchViewState) {
                SearchTaskViewModel.SearchViewState.LOADING.ordinal -> {
                   showLoading()
                }

                SearchTaskViewModel.SearchViewState.SEARCH_RESULT.ordinal -> {
                    showSearchResult()
                }

                SearchTaskViewModel.SearchViewState.INITIAL_SCREEN.ordinal -> {
                    showInitialScreen()
                }

                SearchTaskViewModel.SearchViewState.ERROR.ordinal -> {
                    showAnErrorHappened()
                }

                SearchTaskViewModel.SearchViewState.RESULT_NOT_FOUND.ordinal -> {
                    showNoResultFound()
                }
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecycleView() {
        binding.recycleViewTasks.layoutManager =
            LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        viewModel.searchResult.observe(this, Observer{
            it?.let {
                adapter = SearchTaskAdapter(it, this)
                binding.recycleViewTasks.adapter = adapter

            }
        })
    }

    /** binding the search view */
    private fun bindSearchView() {
        binding.searchView.setBackgroundColor(Color.WHITE)
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            /** delay the search with 400 milliseconds when the last character is typed */
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onQueryTextChange(newText: String): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    newText.let {
                        delay(SEARCH_TIME_DELAY)
                        if (it.isNotEmpty()) {
                            viewModel.searchForTasks(newText)
                        } else {
                            viewModel.clearSearchResult()
                        }
                    }
                }
                return false
            }
        })
    }

    /** showing the init screen */
    private fun showInitialScreen() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleViewTasks.visibility = View.INVISIBLE
        binding.initSearchMsg.visibility = View.VISIBLE
        binding.txtNotResultMsg.visibility = View.INVISIBLE
        binding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE
    }

    /** showing the loading screen */
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recycleViewTasks.visibility = View.INVISIBLE
        binding.initSearchMsg.visibility = View.INVISIBLE
        binding.txtNotResultMsg.visibility = View.INVISIBLE
        binding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE
    }

    /** showing the search screen */
    private fun showSearchResult() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleViewTasks.visibility = View.VISIBLE
        binding.initSearchMsg.visibility = View.INVISIBLE
        binding.txtNotResultMsg.visibility = View.INVISIBLE
        binding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE
    }

    /** showing the result found screen */
    private fun showNoResultFound() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleViewTasks.visibility = View.INVISIBLE
        binding.initSearchMsg.visibility = View.INVISIBLE
        binding.txtNotResultMsg.visibility = View.VISIBLE
        binding.txtSomethingWentWrongMsg.visibility = View.INVISIBLE
    }

    /** showing an error happened screen */
    private fun showAnErrorHappened() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleViewTasks.visibility = View.VISIBLE
        binding.initSearchMsg.visibility = View.INVISIBLE
        binding.txtNotResultMsg.visibility = View.INVISIBLE
        binding.txtSomethingWentWrongMsg.visibility = View.VISIBLE
    }
}
