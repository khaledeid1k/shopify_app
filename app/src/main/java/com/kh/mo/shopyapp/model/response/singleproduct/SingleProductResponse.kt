package com.kh.mo.shopyapp.model.response.singleproduct


import com.google.gson.annotations.SerializedName

data class SingleProductResponse(
    @SerializedName("product")
    val product: SingleProduct?
)