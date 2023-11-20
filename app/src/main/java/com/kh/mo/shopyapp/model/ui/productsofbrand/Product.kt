package com.kh.mo.shopyapp.model.ui.productsofbrand


import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.productsofbrand.ImageResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.VariantResponse

data class Product(


    @SerializedName("images")
    val images: List<ImageResponse?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("variants")
    val variants: List<VariantResponse?>?

)