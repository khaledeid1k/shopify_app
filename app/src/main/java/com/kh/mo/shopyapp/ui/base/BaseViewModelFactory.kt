package com.kh.mo.shopyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import com.kh.mo.shopyapp.ui.sing_in.viewmodel.SignInViewModel
import com.kh.mo.shopyapp.ui.sing_up.viewmodel.SignUpViewModel

class BaseViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repo) as T
            SignUpViewModel::class.java -> SignUpViewModel(repo) as T
            SignInViewModel::class.java -> SignInViewModel(repo) as T
            else -> throw Throwable("Unsupported view model")
        }
    }
}
