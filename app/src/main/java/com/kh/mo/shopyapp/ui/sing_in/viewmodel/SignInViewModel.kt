package com.kh.mo.shopyapp.ui.sing_in.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val repo: Repo) : ViewModel() {

    private val _singIn = MutableStateFlow<ApiState<UserData>>(ApiState.Loading)
    val singIn: StateFlow<ApiState<UserData>> = _singIn

    private val _checkCustomerExists = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val checkCustomerExists: StateFlow<ApiState<String>> = _checkCustomerExists


     fun singInCustomer(email: String) {
        viewModelScope.launch {
            repo.singInCustomer(email).collect {
                when (it) {
                    is ApiState.Failure -> {
                        _singIn.value = ApiState.Failure(it.msg)
                    }
                    ApiState.Loading -> {
                        _singIn.value = ApiState.Loading
                    }
                    is ApiState.Success -> {
                        _singIn.value = ApiState.Success(it.data)
                    }
                }
            }
        }
    }

    fun singInWithFireBase(userData: UserData) {
        viewModelScope.launch {
            repo.singInWithFireBase(userData).collect {
                when (it) {
                    is ApiState.Failure -> {
                        _checkCustomerExists.value = ApiState.Failure(it.msg)
                    }
                    ApiState.Loading -> {
                        _checkCustomerExists.value = ApiState.Loading
                    }
                    is ApiState.Success -> {
                        _checkCustomerExists.value = ApiState.Success(it.data)
                    }
                }
            }
        }
    }

    fun checkIsUserLogin()=repo.checkIsUserLogin()


    fun validateEmail(email: String): Validation = repo.validateEmail(email)

    fun validatePassword(password: String) = repo.validatePassword(password)


}