package com.kh.mo.shopyapp.model.response.allproducts


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("handle")
    val handle: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: ImageResponse,
    @SerializedName("images")
    val images: List<ImageResponse>,
    @SerializedName("options")
    val options: List<OptionResponse>,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("template_suffix")
    val templateSuffix: Any,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("variants")
    val variants: List<VariantResponse>,
    @SerializedName("vendor")
    val vendor: String
)