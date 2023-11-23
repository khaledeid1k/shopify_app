package com.kh.mo.shopyapp.model.response.address


import com.google.gson.annotations.SerializedName

data class AddressesResponse(
    @SerializedName("addresses")
    val addressResponses: List<AddressResponse>
)