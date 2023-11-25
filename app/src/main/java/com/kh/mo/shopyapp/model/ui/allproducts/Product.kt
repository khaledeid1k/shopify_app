package com.kh.mo.shopyapp.model.ui.allproducts


import android.os.Parcelable
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.response.allproducts.OptionResponse
import com.kh.mo.shopyapp.model.response.allproducts.VariantResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id:Long,
    val images: List<ImageResponse>,
    val productType: String,
    val image: ImageResponse,
    val title: String,
    val variants: List<VariantResponse>,
    val options: List<OptionResponse>,
    val vendor: String,
    val status: String,
    var isFavorite: Boolean=false,
) : Parcelable