package com.kh.mo.shopyapp.model.response.barnds


import com.google.gson.annotations.SerializedName

data class RuleResponse(
    @SerializedName("column")
    val column: String,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("relation")
    val relation: String
)