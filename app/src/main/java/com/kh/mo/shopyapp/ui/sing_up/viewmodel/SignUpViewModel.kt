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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG SignUpViewModel"


    private val _createCustomer = MutableStateFlow<ApiState<CustomerEntity>>(ApiState.Loading)
    val createCustomer: StateFlow<ApiState<CustomerEntity>> = _createCustomer

    private val _createFavoriteDraft = MutableStateFlow<ApiState<DraftOrder>>(ApiState.Loading)
    val createFavoriteDraft: StateFlow<ApiState<DraftOrder>> = _createFavoriteDraft

    private val _favoriteDraftIdInFireBase = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val favoriteDraftIdInFireBase: StateFlow<ApiState<String>> = _favoriteDraftIdInFireBase

    private val _saveCustomerFireBase = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val saveCustomerFireBase: StateFlow<ApiState<String>> = _saveCustomerFireBase






    fun createUser(userData: UserData) {
        viewModelScope.launch {
            repo.createCustomer(userData).collect {
                _createCustomer.value=it
            }
        }
    }
    fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest){
        viewModelScope.launch {
            Log.d("TAG", "createFavoriteDraft: $draftOrderRequest")
            repo.createFavoriteDraft(draftOrderRequest).collect{
                _createFavoriteDraft.value=it

            }
        }
    }
     fun createCartDraft(draftOrderRequest: DraftOrderRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createFavoriteDraft(draftOrderRequest)
                .collectLatest {
                    if(it is ApiState.Success){
                        saveCartDraftIdInPreferences(it.data.draftId)
                        saveCartDraftIdAndFavoriteIDInFireBase()
                    }
                }
                }
            }



    private fun saveCartDraftIdAndFavoriteIDInFireBase() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveCartDraftIdAndFavoriteIdInFireBase().collect {
                _favoriteDraftIdInFireBase.value=it
            }
        }
    }
    fun singUpWithFireBase(userData: UserData) {
        viewModelScope.launch {
            saveCustomerEmail(userData.email)
            saveCustomerUserName(userData.userName)
            repo.singUpWithFireBase(userData).collect {
                _saveCustomerFireBase.value=it
            }

        }
    }

     fun saveCustomerIdAndFavoriteDraftId(customerId:Long, favoriteDraft:Long){
        repo.saveCustomerId(customerId)
        repo.saveFavoriteDraftId(favoriteDraft)
    }
    private fun saveCustomerEmail(customerEmail: String){
        repo.saveCustomerEmail(customerEmail)
    }
    private fun saveCustomerUserName(customerUserName: String){
        repo.saveCustomerUserName(customerUserName)
    }
    fun validateUserName(userName: String) = repo.validateUserName(userName)
    fun validateEmail(email: String): Validation = repo.validateEmail(email)
    fun validatePassword(password: String) = repo.validatePassword(password)
    fun validateConfirmPassword(password: String, rePassword: String) =
        repo.validateConfirmPassword(password, rePassword)


    fun getCustomerId()=repo.getCustomerId()



    private fun saveCartDraftIdInPreferences(draftId: Long) {
        viewModelScope.launch(Dispatchers.IO) { repo.saveCartDraftId(draftId) }
    }

}