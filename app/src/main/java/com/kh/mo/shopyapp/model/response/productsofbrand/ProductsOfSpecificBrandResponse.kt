package com.kh.mo.shopyapp.model.response.productsofbrand


import com.google.gson.annotations.SerializedName

data class ProductsOfSpecificBrandResponse(
    @SerializedName("products")
    val products: List<ProductResponse?>?
)