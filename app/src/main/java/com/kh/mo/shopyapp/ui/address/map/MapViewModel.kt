package com.kh.mo.shopyapp.ui.address.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG MapViewModel"
    private val _addressDetailsState: MutableSharedFlow<ApiState<Address>> =
        MutableSharedFlow()
    val addressDetailsState: MutableSharedFlow<ApiState<Address>>
        get() = _addressDetailsState

    fun getAddressDetails(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAddressDetails(latitude, longitude).collectLatest {
                _addressDetailsState.emit(it)
            }
        }
    }
}