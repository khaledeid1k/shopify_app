package com.kh.mo.shopyapp.model.ui.orderdetails

import com.kh.mo.shopyapp.model.response.orderdetails.PriceSet


data class LineItem(

    val giftCard: Boolean?,
    val name: String?,
    val price: String?,
    val productId: Long?,
    val quantity: Int?,
    val title: String?,
    val totalDiscount: String?,
    val priceSet: PriceSet?

)