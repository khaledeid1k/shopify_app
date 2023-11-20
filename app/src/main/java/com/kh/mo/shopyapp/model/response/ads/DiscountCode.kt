package com.kh.mo.shopyapp.model.response.ads


import com.google.gson.annotations.SerializedName

data class DiscountCode(
    @SerializedName("code")
    val code: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("price_rule_id")
    val priceRuleId: Long?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("usage_count")
    val usageCount: Int?
)