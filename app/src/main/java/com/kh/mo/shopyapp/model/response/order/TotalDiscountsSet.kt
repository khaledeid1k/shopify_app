package com.kh.mo.shopyapp.model.response.order


import com.google.gson.annotations.SerializedName

data class TotalDiscountsSet(
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoney?,
    @SerializedName("shop_money")
    val shopMoney: ShopMoney?
)