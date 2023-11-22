package com.kh.mo.shopyapp.model.response.barnds


import com.google.gson.annotations.SerializedName

data class SmartCollection(
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: ImageResponse
)