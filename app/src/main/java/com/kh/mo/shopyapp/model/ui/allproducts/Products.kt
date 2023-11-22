package com.kh.mo.shopyapp.model.ui.allproducts


import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.response.allproducts.VariantResponse

data class Products(
    @SerializedName("images")
    val images: List<ImageResponse>,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("image")
    val image: ImageResponse,
    @SerializedName("title")
    val title: String,
    @SerializedName("variants")
    val variants: List<VariantResponse>

)