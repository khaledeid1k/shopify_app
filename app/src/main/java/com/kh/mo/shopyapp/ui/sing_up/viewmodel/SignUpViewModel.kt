package com.kh.mo.shopyapp.ui.sing_up.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val repo: Repo):ViewModel() {

    private val _saveCustomer = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val saveCustomer: StateFlow<ApiState<String>> = _saveCustomer


    fun storeData(userId: String, userData: UserData) {
        viewModelScope.launch {
            repo.storeData(userId, userData).collect{
                when(it){
                    is ApiState.Failure -> Log.d("sadasdasdasd", "storeData:Failure ")
                    ApiState.Loading ->Log.d("sadasdasdasd", "storeData:Loading ")
                    is ApiState.Success -> Log.d("sadasdasdasd", "storeData:Success ")
                }


            }

        }
    }

}