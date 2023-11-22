package com.kh.mo.shopyapp.model.response.barnds


import com.google.gson.annotations.SerializedName

data class BrandsResponse(
    @SerializedName("smart_collections")
    val smartCollections: List<SmartCollectionRespnse>
)