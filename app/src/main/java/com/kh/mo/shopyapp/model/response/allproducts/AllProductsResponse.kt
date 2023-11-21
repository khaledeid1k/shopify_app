package com.kh.mo.shopyapp.model.response.allproducts


import com.google.gson.annotations.SerializedName

data class AllProductsResponse(
    @SerializedName("products")
    val products: List<ProductResponse?>?
)