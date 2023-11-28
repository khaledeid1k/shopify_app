package com.kh.mo.shopyapp.model.response.ads


import com.google.gson.annotations.SerializedName

data class PrerequisiteToEntitlementPurchase(
    @SerializedName("prerequisite_amount")
    val prerequisiteAmount: Any
)