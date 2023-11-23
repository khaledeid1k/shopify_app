package com.kh.mo.shopyapp.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG ProfileViewModel"
    private val _userData: MutableStateFlow<UserData?> =
        MutableStateFlow(UserData(7588056039708, "Moaaz AbdEl-salam", "moaaz_1@gmail.com"))
    val userData: MutableStateFlow<UserData?>
        get() = _userData

    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            _userData.value?.let {userData ->
                Log.i(TAG, "getAddresses of user $userData")
                repo.getAddressesOfCustomer(userData.id).collectLatest {addressResponse ->
                    when(addressResponse) {
                        is ApiState.Failure -> Log.i(
                            TAG,
                            "getAddresses: failures: ${addressResponse.msg}"
                        )
                        ApiState.Loading -> Log.i(TAG, "getAddresses: loading")
                        is ApiState.Success -> Log.i(
                            TAG,
                            "getAddresses: success: ${addressResponse.data}"
                        )
                    }
                }
            }
        }
    }
}