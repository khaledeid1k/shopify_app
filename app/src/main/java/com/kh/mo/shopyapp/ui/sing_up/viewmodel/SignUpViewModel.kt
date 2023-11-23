package com.kh.mo.shopyapp.ui.sing_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val repo: Repo) : ViewModel() {

    private val _saveCustomerFireBase = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val saveCustomerFireBase: StateFlow<ApiState<String>> = _saveCustomerFireBase

    private val _createCustomer = MutableStateFlow<ApiState<CustomerEntity>>(ApiState.Loading)
    val createCustomer: StateFlow<ApiState<CustomerEntity>> = _createCustomer

    fun singUpWithFireBase(userData: UserData) {
        viewModelScope.launch {
            repo.singUpWithFireBase(userData).collect {
                when (it) {
                    is ApiState.Failure -> {
                        _saveCustomerFireBase.value = ApiState.Failure(it.msg)
                    }
                    is ApiState.Loading -> {
                        _saveCustomerFireBase.value = ApiState.Loading
                    }
                    is ApiState.Success -> {
                        _saveCustomerFireBase.value = ApiState.Success(it.data)
                    }
                }


            }

        }
    }

    fun createUser(userData: UserData) {
        viewModelScope.launch {
            repo.createCustomer(userData).collect {
                when (it) {
                    is ApiState.Failure -> {
                        _createCustomer.value = ApiState.Failure(it.msg)
                    }
                    is ApiState.Loading -> {
                        _createCustomer.value = ApiState.Loading
                    }
                    is ApiState.Success -> {
                        _createCustomer.value = ApiState.Success(it.data)
                    }
                }
            }
        }
    }
    fun validateUserName(userName: String) = repo.validateUserName(userName)

    fun validateEmail(email: String): Validation = repo.validateEmail(email)

    fun validatePassword(password: String) = repo.validatePassword(password)

    fun validateConfirmPassword(password: String, rePassword: String) =
        repo.validateConfirmPassword(password, rePassword)




}