package com.kh.mo.shopyapp.model.response.ads


import com.google.gson.annotations.SerializedName

data class DiscountCodeResponse(
    @SerializedName("discount_code")
    val discountCode: DiscountCode?
)