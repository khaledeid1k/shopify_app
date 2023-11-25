package com.kh.mo.shopyapp.model.ui.order

import com.kh.mo.shopyapp.model.response.order.CustomerResponse
import com.kh.mo.shopyapp.model.response.order.LineItemResponse


data class Order(
    val currency: String?,
    val currentTotalPrice: String?,
    val customerResponse: CustomerResponse?,
    val lineItems: List<LineItemResponse>?,
    val subtotalPrice: String?,
    val id: Long?

)
