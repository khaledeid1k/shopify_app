package com.kh.mo.shopyapp.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.ui.sing_in.viewmodel.SignInViewModel
import com.kh.mo.shopyapp.ui.sing_up.viewmodel.SignUpViewModel

class BaseViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {

          SignUpViewModel::class.java ->  SignUpViewModel(repo) as T
            else ->  throw Throwable("Unsupported view model")
        }
    }
}
