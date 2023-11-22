package com.kh.mo.shopyapp.model.ui.maincategory


import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.maincategory.ImageResponse

data class CustomCollection(
    @SerializedName("image")
    val image: ImageResponse?,
    val title: String?,
    @SerializedName("id")
    val id: Long?
)
