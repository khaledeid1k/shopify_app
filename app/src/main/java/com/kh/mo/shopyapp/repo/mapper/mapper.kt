package com.kh.mo.shopyapp.repo.mapper

import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.allproducts.Products
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection

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

fun AddressResponse.convertToAddressEntity() =
    Address(
        address = this.address1,
        markLocation = this.address2,
        city = this.city,
        country = this.country,
        default = this.default,
        name = this.name,
        phone = this.phone,
        state = this.province
    )
fun BrandsResponse.convertToSmartCollection():List<SmartCollection>{

    return this.smartCollections.map {

        SmartCollection(it.title,it.image)
    }

}

fun MainCategoryResponse.convertToCustomCollection():List<CustomCollection>{

    return this.customCollections?.map {
        CustomCollection(it?.image,it?.title,it?.id)
    } ?: emptyList()
}


fun AllProductsResponse.convertToAllProducts():List<Products>{

    return this.products.map {
        Products(
            images = it.images,
            productType = it.productType,
            image = it.image,
            title = it.title,
            variants = it.variants,
            options = it.options,
            vendor = it.vendor,
            status = it.status
        )
    }

}

fun DraftOrderResponse.convertDraftOrderResponseToDraftOrder(): DraftOrder {
    return DraftOrder(
        this.draft_order!!.id,
        this.draft_order.customer.id
    )

}
