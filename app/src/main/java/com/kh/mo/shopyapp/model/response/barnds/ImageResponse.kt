package com.kh.mo.shopyapp.model.response.barnds


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("alt")
    val alt: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("src")
    val src: String,
    @SerializedName("width")
    val width: Int
)