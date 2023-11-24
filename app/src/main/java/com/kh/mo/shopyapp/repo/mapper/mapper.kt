package com.kh.mo.shopyapp.repo.mapper

import com.kh.mo.shopyapp.model.entity.*
import com.kh.mo.shopyapp.model.request.*
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.response.allproducts.OptionResponse
import com.kh.mo.shopyapp.model.response.allproducts.VariantResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.allproducts.Product
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

fun BrandsResponse.convertToSmartCollection(): List<SmartCollection> {

    return this.smartCollections.map {

        SmartCollection(it.title, it.image)
    }

}

fun MainCategoryResponse.convertToCustomCollection(): List<CustomCollection> {

    return this.customCollections?.map {
        CustomCollection(it?.image, it?.title, it?.id)
    } ?: emptyList()
}


fun AllProductsResponse.convertToAllProducts(): List<Product> {

    return this.products.map {
        Product(
            id = it.id,
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


fun DraftOrderResponse.convertDraftOrderResponseToLineItemEntity(): LineItemEntity {
    return draft_order!!.line_items!![0].let {
        LineItemEntity(
            product_id = it.product_id!!,
            variant_id = it.variant_id!!
        )
    }
}


fun Product.convertProductToFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id,
        images.convertImagesToImagesEntity(),
        productType,
        image.convertImageToImageEntity(),
        title,
        variants.convertVariantsToVariantsEntity(),
        options.convertOptionsToOptionsEntity(),
        vendor,
        status
    )
}

fun List<ImageResponse>.convertImagesToImagesEntity(): List<ImageEntity> {
    return map {
        it.convertImageToImageEntity()
    }
}

fun ImageResponse.convertImageToImageEntity(): ImageEntity {
    return ImageEntity(src)
}

fun List<VariantResponse>.convertVariantsToVariantsEntity(): List<VariantEntity> {
    return map {
        VariantEntity(
            it.id,
            it.price,
            it.productId,
            it.title,
            it.weightUnit
        )
    }
}

fun List<OptionResponse>.convertOptionsToOptionsEntity(): List<OptionEntity> {
    return map {
        OptionEntity(
            it.values
        )
    }
}


fun List<FavoriteEntity>.convertFavoritesEntityToDraftOrderRequest(customerId: Long): DraftOrderRequest {
  return  DraftOrderRequest(
        DraftOrderDetailsRequest(
            map {
                LineItems(product_id = it.id.toString(), variant_id = it.variants[0].id)
            },
            CustomerDraftRequest(customerId)
        )
    )

}
