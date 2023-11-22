package com.kh.mo.shopyapp.model.ui.allproducts


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.response.allproducts.OptionResponse
import com.kh.mo.shopyapp.model.response.allproducts.VariantResponse
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val variants: List<VariantResponse>,
    @SerializedName("options")
    val options: List<OptionResponse?>?,
    val vendor: String?,
    val status: String?,
) : Parcelable