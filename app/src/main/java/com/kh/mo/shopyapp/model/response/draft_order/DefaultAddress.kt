package com.kh.mo.shopyapp.model.response.draft_order

data class DefaultAddress(
    val address1: String?,
    val address2: String?,
    val city: String?,
    val company: Any?,
    val country: String?,
    val country_code: String?,
    val country_name: String?,
    val customer_id: Long?,
    val default: Boolean?,
    val first_name: String?,
    val id: Long?,
    val last_name: String?,
    val name: String?,
    val phone: String?,
    val province: String?,
    val province_code: String?,
    val zip: Any?
)