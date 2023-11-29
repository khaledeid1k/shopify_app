package com.kh.mo.shopyapp.model.response.ads


import com.google.gson.annotations.SerializedName

data class PriceRule(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("allocation_limit")
    val allocationLimit: Any,
    @SerializedName("allocation_method")
    val allocationMethod: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("customer_segment_prerequisite_ids")
    val customerSegmentPrerequisiteIds: List<Any>,
    @SerializedName("customer_selection")
    val customerSelection: String,
    @SerializedName("ends_at")
    val endsAt: Any,
    @SerializedName("entitled_collection_ids")
    val entitledCollectionIds: List<Any>,
    @SerializedName("entitled_country_ids")
    val entitledCountryIds: List<Any>,
    @SerializedName("entitled_product_ids")
    val entitledProductIds: List<Any>,
    @SerializedName("entitled_variant_ids")
    val entitledVariantIds: List<Any>,
    @SerializedName("id")
    val id: Long,
    @SerializedName("once_per_customer")
    val oncePerCustomer: Boolean,
    @SerializedName("prerequisite_collection_ids")
    val prerequisiteCollectionIds: List<Any>,
    @SerializedName("prerequisite_customer_ids")
    val prerequisiteCustomerIds: List<Any>,
    @SerializedName("prerequisite_product_ids")
    val prerequisiteProductIds: List<Any>,
    @SerializedName("prerequisite_quantity_range")
    val prerequisiteQuantityRange: Any,
    @SerializedName("prerequisite_shipping_price_range")
    val prerequisiteShippingPriceRange: Any,
    @SerializedName("prerequisite_subtotal_range")
    val prerequisiteSubtotalRange: Any,
    @SerializedName("prerequisite_to_entitlement_purchase")
    val prerequisiteToEntitlementPurchase: PrerequisiteToEntitlementPurchase,
    @SerializedName("prerequisite_to_entitlement_quantity_ratio")
    val prerequisiteToEntitlementQuantityRatio: PrerequisiteToEntitlementQuantityRatio,
    @SerializedName("prerequisite_variant_ids")
    val prerequisiteVariantIds: List<Any>,
    @SerializedName("starts_at")
    val startsAt: String,
    @SerializedName("target_selection")
    val targetSelection: String,
    @SerializedName("target_type")
    val targetType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("usage_limit")
    val usageLimit: Any,
    @SerializedName("value")
    val value: String,
    @SerializedName("value_type")
    val valueType: String
)