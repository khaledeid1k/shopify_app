package com.kh.mo.shopyapp.model.request

import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.address.AddressResponse

data class AddressUpdateRequest(
    @SerializedName("customer_address")
    val customerAddressResponse: AddressResponse
)
