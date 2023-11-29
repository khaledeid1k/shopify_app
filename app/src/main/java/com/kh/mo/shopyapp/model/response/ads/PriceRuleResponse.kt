package com.kh.mo.shopyapp.model.response.ads


import com.google.gson.annotations.SerializedName

data class PriceRuleResponse(
    @SerializedName("price_rule")
    val priceRule: PriceRule
)