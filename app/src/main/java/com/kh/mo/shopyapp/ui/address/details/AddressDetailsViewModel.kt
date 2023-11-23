package com.kh.mo.shopyapp.ui.address.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddressDetailsViewModel(private val repo: Repo): ViewModel() {

    private val _updateAddressState: MutableStateFlow<ApiState<Address>> =
        MutableStateFlow(ApiState.Loading)
    val updateAddressState: MutableStateFlow<ApiState<Address>>
        get() = _updateAddressState

    fun updateAddress(
        customerId: Long,
        addressId: Long,
        updatedAddress: Address
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateAddressOfCustomer(customerId, addressId, updatedAddress).collectLatest {
                _updateAddressState.value = it
            }
        }
    }
}