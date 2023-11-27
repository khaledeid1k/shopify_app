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

    private val _checkCustomerExists = MutableStateFlow<ApiState<Long>>(ApiState.Loading)
    val checkCustomerExists: StateFlow<ApiState<Long>> = _checkCustomerExists

    private val _draftFavoriteId = MutableStateFlow<ApiState<String?>>(ApiState.Loading)
    val draftFavoriteId: StateFlow<ApiState<String?>> = _draftFavoriteId

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
                        saveCustomerId(it.data.id)
                        saveCustomerEmail(it.data.email)
                        saveCustomerUserName(it.data.userName)
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
                        _checkCustomerExists.value = ApiState.Success(userData.id)

                    }
                }
            }
        }
    }

    fun checkIsUserLogin()=repo.checkIsUserLogin()


    fun validateEmail(email: String): Validation = repo.validateEmail(email)

    fun validatePassword(password: String) = repo.validatePassword(password)

    private fun saveCustomerId(customerId:Long){
        repo.saveCustomerId(customerId)
    }
    private fun saveCustomerEmail(customerEmail: String){
        repo.saveCustomerEmail(customerEmail)
    }
    private fun saveCustomerUserName(customerUserName: String){
        repo.saveCustomerUserName(customerUserName)
    }
     fun saveFavoriteDraftId(favoriteDraft:Long){
        repo.saveFavoriteDraftId(favoriteDraft)
    }
     fun getDraftFavoriteId(customerId: Long) {
        viewModelScope.launch {
            repo.getDraftFavoriteId(customerId.toString()).collect{
                _draftFavoriteId.value=it

            }
        }
    }

    fun getCustomerId()=repo.getCustomerId()

}