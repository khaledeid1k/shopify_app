package com.kh.mo.shopyapp.model.request.order


import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("order")
    val order: Order
)