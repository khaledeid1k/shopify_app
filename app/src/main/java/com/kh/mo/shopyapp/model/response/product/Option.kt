package com.kh.mo.shopyapp.model.response.product

data class Option(
    val id: Long?,
    val name: String?,
    val position: Int?,
    val product_id: Long?,
    val values: List<String?>?
)