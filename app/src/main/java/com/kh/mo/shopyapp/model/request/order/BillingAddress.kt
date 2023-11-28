package com.kh.mo.shopyapp.model.request.order


import com.google.gson.annotations.SerializedName

data class BillingAddress(
    @SerializedName("address1")
    val address1: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("zip")
    val zip: String?
)