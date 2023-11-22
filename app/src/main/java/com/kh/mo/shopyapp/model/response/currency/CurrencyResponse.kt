package com.kh.mo.shopyapp.model.response.currency


import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Rates
)