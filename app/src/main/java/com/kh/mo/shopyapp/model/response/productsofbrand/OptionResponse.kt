package com.kh.mo.shopyapp.model.response.productsofbrand


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable