package com.kh.mo.shopyapp.model.response.orderdetails


import com.google.gson.annotations.SerializedName

data class CurrentTotalTaxSet(
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoney?,
    @SerializedName("shop_money")
    val shopMoney: ShopMoney?
)