package com.kh.mo.shopyapp.ui.sing_up.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class SignUpViewModel(private val repo: Repo) : ViewModel() {

    private val _saveCustomerFireBase = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val saveCustomerFireBase: StateFlow<ApiState<String>> = _saveCustomerFireBase

    private val _createCustomer = MutableStateFlow<ApiState<CustomerEntity>>(ApiState.Loading)
    val createCustomer: StateFlow<ApiState<CustomerEntity>> = _createCustomer


    private val _createFavoriteDraft = MutableStateFlow<ApiState<DraftOrder>>(ApiState.Loading)
    val createFavoriteDraft: StateFlow<ApiState<DraftOrder>> = _createFavoriteDraft

    private val _favoriteDraftIdInFireBase = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val favoriteDraftIdInFireBase: StateFlow<ApiState<String>> = _favoriteDraftIdInFireBase


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
    fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest){
        viewModelScope.launch {
            Log.d("TAG", "createFavoriteDraft: $draftOrderRequest")
            repo.createFavoriteDraft(draftOrderRequest).collect{
                when(it){
                    is ApiState.Failure ->{
                        _createFavoriteDraft.value= ApiState.Failure(it.msg)}
                    is ApiState.Loading -> {
                        _createFavoriteDraft.value= ApiState.Loading}
                    is ApiState.Success -> {
                        _createFavoriteDraft.value=  ApiState.Success(it.data)}
                }
            }
        }
    }
    fun saveFavoriteDraftIdInFireBase(customerId:Long,favoriteDraft:Long){
        viewModelScope.launch {
            repo.saveFavoriteDraftIdInFireBase(customerId,favoriteDraft).collect{
                when(it){
                    is ApiState.Failure -> {_favoriteDraftIdInFireBase.value=ApiState.Failure(it.msg)}
                    is ApiState.Loading -> {_favoriteDraftIdInFireBase.value=ApiState.Loading }
                    is ApiState.Success ->{_favoriteDraftIdInFireBase.value=ApiState.Success(it.data)}
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