package com.kh.mo.shopyapp.model.response.orderdetails


import com.google.gson.annotations.SerializedName

data class OrderDetailsResponse(
    @SerializedName("order")
    val order: OrderResponse?
)