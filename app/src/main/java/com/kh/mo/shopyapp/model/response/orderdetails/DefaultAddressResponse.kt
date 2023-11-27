package com.kh.mo.shopyapp.model.response.orderdetails


import com.google.gson.annotations.SerializedName

data class DefaultAddressResponse(
    @SerializedName("address1")
    val address1: String?,
    @SerializedName("address2")
    val address2: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("company")
    val company: Any?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("country_name")
    val countryName: String?,
    @SerializedName("customer_id")
    val customerId: Long?,
    @SerializedName("default")
    val default: Boolean?,
    @SerializedName("first_name")
    val firstName: Any?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("province_code")
    val provinceCode: String?,
    @SerializedName("zip")
    val zip: String?
)