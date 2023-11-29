package com.kh.mo.shopyapp.model.request.order


import com.google.gson.annotations.SerializedName

data class OrderDiscountCode(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("type")
    val type: String?
)