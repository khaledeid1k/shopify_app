package com.kh.mo.shopyapp.model.ui.orderdetails

import com.kh.mo.shopyapp.model.response.order.CustomerResponse
import com.kh.mo.shopyapp.model.response.order.LineItemResponse


data class Order(
    val confirmed: Boolean?,
    val contactEmail: String?,
    val createdAt: String?,
    val currency: String?,
    val currentTotalDiscounts: String?,
    val currentTotalPrice: String?,
    val customer: CustomerResponse?,
    val id: Long?,
    val lineItems: List<LineItemResponse>?,
    val name: String?,
    val orderNumber: Int?,
    val paymentGatewayNames: List<Any>?,
    val phone: Any?,
    val refunds: List<Any>?,
    val subtotalPrice: String?,
    val totalDiscounts: String?,
    val totalPrice: String?,
    val totalTax: String?

)