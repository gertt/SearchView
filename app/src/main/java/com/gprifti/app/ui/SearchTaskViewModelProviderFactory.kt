package com.gprifti.app.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gprifti.app.repository.SearchRepository

/** Boilerplate code*/
class SearchTaskViewModelProviderFactory(
    private val app: Application,
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchTaskViewModel(app, searchRepository) as T
    }
}