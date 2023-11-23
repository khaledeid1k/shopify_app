package com.kh.mo.shopyapp.ui.address

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddressViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG AddressViewModel"

    private val _userAddresses: MutableStateFlow<ApiState<List<Address>>> =
        MutableStateFlow(ApiState.Loading)
    val userAddresses: StateFlow<ApiState<List<Address>>>
        get() = _userAddresses

    fun getAddresses(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "getAddresses of user $userId")
            repo.getAddressesOfCustomer(userId).collectLatest { addressResponse ->
                if (addressResponse is ApiState.Success && addressResponse.data.isEmpty())
                    _userAddresses.value = ApiState.Failure("Empty Data")
                else
                    _userAddresses.value = addressResponse
            }
        }
    }
}