package com.kh.mo.shopyapp.model.request.order


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("billing_address")
    val billingAddress: BillingAddress,
    @SerializedName("discount_codes")
    val discountCodes: List<OrderDiscountCode>,
    @SerializedName("email")
    val email: String,
    @SerializedName("financial_status")
    val financialStatus: String,
    @SerializedName("line_items")
    val lineItems: List<LineItem>,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("shipping_address")
    val shippingAddress: ShippingAddress
)