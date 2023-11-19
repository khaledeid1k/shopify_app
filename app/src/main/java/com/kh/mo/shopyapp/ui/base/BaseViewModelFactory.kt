package com.kh.mo.shopyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.shopyapp.repo.Repo

class BaseViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
           // SettingsViewModel::class.java -> SettingsViewModel(repo) as T
            else -> throw Throwable("Unsupported view model")
        }
    }
}
