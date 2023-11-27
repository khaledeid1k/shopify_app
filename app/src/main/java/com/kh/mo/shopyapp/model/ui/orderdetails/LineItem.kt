package com.kh.mo.shopyapp.model.ui.orderdetails



data class LineItem(

    val giftCard: Boolean?,
    val name: String?,
    val price: String?,
    val productId: Long?,
    val quantity: Int?,
    val title: String?,
    val totalDiscount: String?

)