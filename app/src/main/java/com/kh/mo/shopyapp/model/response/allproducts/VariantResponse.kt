package com.kh.mo.shopyapp.model.response.allproducts


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariantResponse(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("compare_at_price")
    val compareAtPrice: Double,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("fulfillment_service")
    val fulfillmentService: String,
    @SerializedName("grams")
    val grams: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image_id")
    val imageId: Long,
    @SerializedName("inventory_item_id")
    val inventoryItemId: Long,
    @SerializedName("inventory_management")
    val inventoryManagement: String,
    @SerializedName("inventory_policy")
    val inventoryPolicy: String,
    @SerializedName("inventory_quantity")
    val inventoryQuantity: Int,
    @SerializedName("old_inventory_quantity")
    val oldInventoryQuantity: Int,
    @SerializedName("option1")
    val option1: String,
    @SerializedName("option2")
    val option2: String,
    @SerializedName("option3")
    val option3: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: Long,
    @SerializedName("requires_shipping")
    val requiresShipping: Boolean?,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("taxable")
    val taxable: Boolean?,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("weight")
    val weight: Double?,
    @SerializedName("weight_unit")
    val weightUnit: String
) : Parcelable