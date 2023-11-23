package com.kh.mo.shopyapp.repo.mapper

import com.kh.mo.shopyapp.model.entity.AddressEntity
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.address.Address
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.login.Login

fun CustomerResponse.convertCustomerResponseToCustomerEntity(): CustomerEntity {
    return CustomerEntity(
        customer?.id!!,
        customer.first_name!!,
        customer.email!!, true
    )
}


fun UserData.convertUserDataToCustomerData(): CustomerDataRequest {
    return CustomerDataRequest(
        userName, email, true, password, password
    )
}

fun Login.convertLoginToUserData(): UserData {
    return this.customers?.get(0).let {
        UserData(id = it?.id!!, it.first_name.toString(), it.email.toString())
    }
}

fun Address.convertToAddressEntity() =
    AddressEntity(
        address = this.address1,
        markLocation = this.address2,
        city = this.city,
        country = this.country,
        default = this.default,
        name = this.name,
        phone = this.phone,
        state = this.province
    )