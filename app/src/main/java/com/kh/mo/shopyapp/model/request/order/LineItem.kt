package com.kh.mo.shopyapp.model.request.order


import com.google.gson.annotations.SerializedName

data class LineItem(
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("variant_id")
    val variantId: Long?
)