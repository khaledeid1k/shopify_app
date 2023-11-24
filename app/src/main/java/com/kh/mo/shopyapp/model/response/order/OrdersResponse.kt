package com.kh.mo.shopyapp.model.response.order


import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("orders")
    val orders: List<OrderResponse>?
)