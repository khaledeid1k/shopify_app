package com.kh.mo.shopyapp.model.entity

data class CustomerEntity(
    val id: Long,
    val first_name: String,
    val email: String,
    val verified_email: Boolean,
)
