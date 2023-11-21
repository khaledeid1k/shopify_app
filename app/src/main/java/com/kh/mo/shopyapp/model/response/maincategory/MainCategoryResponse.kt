package com.kh.mo.shopyapp.model.response.maincategory


import com.google.gson.annotations.SerializedName

data class MainCategoryResponse(
    @SerializedName("custom_collections")
    val customCollections: List<CustomCollectionResponse?>?
)