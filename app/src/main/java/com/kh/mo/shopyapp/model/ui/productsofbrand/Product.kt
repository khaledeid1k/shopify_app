package com.kh.mo.shopyapp.model.ui.productsofbrand


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.productsofbrand.ImageResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.OptionResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.VariantResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("images")
    val images: List<ImageResponse?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("variants")
    val variants: List<VariantResponse?>?,
    @SerializedName("options")
    val options: List<OptionResponse?>?,
) : Parcelable