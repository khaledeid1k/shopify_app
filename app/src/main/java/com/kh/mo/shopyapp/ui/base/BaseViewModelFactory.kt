package com.kh.mo.shopyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel

class BaseViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
           // SettingsViewModel::class.java -> SettingsViewModel(repo) as T
            HomeViewModel::class.java->HomeViewModel(repo) as T
            else -> throw Throwable("Unsupported view model")
        }
    }
}
