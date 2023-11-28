package com.kh.mo.shopyapp.model.response.ads


import com.google.gson.annotations.SerializedName

data class PrerequisiteToEntitlementQuantityRatio(
    @SerializedName("entitled_quantity")
    val entitledQuantity: Any,
    @SerializedName("prerequisite_quantity")
    val prerequisiteQuantity: Any
)