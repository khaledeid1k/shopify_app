package com.kh.mo.shopyapp.model.response.allproducts


import com.google.gson.annotations.SerializedName

data class OptionResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("position")
    val position: Int?,
    @SerializedName("product_id")
    val productId: Long?,
    @SerializedName("values")
    val values: List<String?>?
)