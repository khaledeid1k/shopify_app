package com.kh.mo.shopyapp.model.response.order


import com.google.gson.annotations.SerializedName

data class LineItemResponse(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String?,
    @SerializedName("attributed_staffs")
    val attributedStaffs: List<Any>?,
    @SerializedName("discount_allocations")
    val discountAllocations: List<Any>?,
    @SerializedName("duties")
    val duties: List<Any>?,
    @SerializedName("fulfillable_quantity")
    val fulfillableQuantity: Int?,
    @SerializedName("fulfillment_service")
    val fulfillmentService: String?,
    @SerializedName("fulfillment_status")
    val fulfillmentStatus: Any?,
    @SerializedName("gift_card")
    val giftCard: Boolean?,
    @SerializedName("grams")
    val grams: Int?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("price_set")
    val priceSet: PriceSet?,
    @SerializedName("product_exists")
    val productExists: Boolean?,
    @SerializedName("product_id")
    val productId: Long?,
    @SerializedName("properties")
    val properties: List<Any>?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("requires_shipping")
    val requiresShipping: Boolean?,
    @SerializedName("sku")
    val sku: String?,
    @SerializedName("tax_lines")
    val taxLines: List<Any>?,
    @SerializedName("taxable")
    val taxable: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("total_discount")
    val totalDiscount: String?,
    @SerializedName("total_discount_set")
    val totalDiscountSet: TotalDiscountSet?,
    @SerializedName("variant_id")
    val variantId: Long?,
    @SerializedName("variant_inventory_management")
    val variantInventoryManagement: String?,
    @SerializedName("variant_title")
    val variantTitle: String?,
    @SerializedName("vendor")
    val vendor: String?
)