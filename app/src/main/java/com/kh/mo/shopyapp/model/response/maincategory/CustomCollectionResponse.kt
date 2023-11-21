package com.kh.mo.shopyapp.model.response.maincategory


import com.google.gson.annotations.SerializedName

data class CustomCollectionResponse(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String?,
    @SerializedName("body_html")
    val bodyHtml: String?,
    @SerializedName("handle")
    val handle: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("image")
    val image: ImageResponse?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("published_scope")
    val publishedScope: String?,
    @SerializedName("sort_order")
    val sortOrder: String?,
    @SerializedName("template_suffix")
    val templateSuffix: Any?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)