package com.kh.mo.shopyapp.model.response.orderdetails


import com.google.gson.annotations.SerializedName

data class EmailMarketingConsent(
    @SerializedName("consent_updated_at")
    val consentUpdatedAt: Any?,
    @SerializedName("opt_in_level")
    val optInLevel: String?,
    @SerializedName("state")
    val state: String?
)