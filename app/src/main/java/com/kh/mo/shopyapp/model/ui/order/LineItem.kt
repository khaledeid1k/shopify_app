package com.kh.mo.shopyapp.model.ui.order

data class LineItem(
    val quantity: Int?,
    val title: String?,
    val price: String?,
    val productId: Long?
)
