package com.kh.mo.shopyapp.model.response.address


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("addresses")
    val addresses: List<Address>
)