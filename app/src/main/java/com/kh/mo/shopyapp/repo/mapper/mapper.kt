package com.kh.mo.shopyapp.repo.mapper

import com.kh.mo.shopyapp.model.entity.*
import com.kh.mo.shopyapp.model.request.*
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.response.allproducts.OptionResponse
import com.kh.mo.shopyapp.model.response.allproducts.VariantResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.osm.NominatimResponse
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.response.order.OrderResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.response.osm.NominatimResponse
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.Favorite
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.model.ui.allproducts.ProductImage
import com.kh.mo.shopyapp.model.ui.allproducts.ProductOption
import com.kh.mo.shopyapp.model.ui.allproducts.ProductVariant
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.model.ui.order.Image
import com.kh.mo.shopyapp.model.ui.order.LineItem
import com.kh.mo.shopyapp.model.ui.order.Order

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

fun AddressResponse.convertToAddress() =
    Address(
        customerId = customerId,
        id = this.id,
        address = this.address1,
        markLocation = this.address2,
        city = this.city,
        country = this.country,
        default = this.default ?: false,
        name = this.name,
        phone = this.phone,
        state = this.province,
        stateCode = this.provinceCode ?: "",
        zip = this.zip
    )

fun NominatimResponse.convertToAddress() =
    Address(
        address = this.displayName,
        city = this.address?.city,
        country = this.address?.country,
        name = this.type,
        phone = "",
        state = this.address?.region,
        zip = this.address?.postcode?.toLong(),
        default = false,
        stateCode = this.address?.ISO?.split("-")?.get(1) ?: ""
    )

fun Address.convertToAddressRequest() =
    AddressRequest(
        AddressResponse(
            this.address,
            this.markLocation,
            this.city,
            country = this.country,
            customerId = this.customerId,
            id = this.id,
            name = this.name,
            phone = this.phone,
            zip = this.zip,
            province = "",
            provinceCode = this.stateCode,
            default = this.default
        )
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


fun AllProductsResponse.convertAllProductsResponseToProducts(): List<Product> {

    return this.products.map { it ->
        Product(
            id = it.id,
            productImages = it.images.map { ProductImage(it.src) },
            productType = it.productType,
            productImage = ProductImage(it.image.src),
            title = it.title,
            productVariants = it.variants.map { ProductVariant(
                it.id!!,it.price!!, it.productId!!, it.title!!,it.weightUnit!!) },
            productOptions =it.options.map {  ProductOption(it.values)} ,
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

fun OrdersResponse.convertToOrders(): List<Order> {
    return this.orders?.map {
        Order(
            it.currency, it.totalPrice, it.customerResponse, it.lineItems,it.subtotalPrice,it.id)
    } ?: emptyList()
}
fun OrderResponse.convertToLineItem(): List<LineItem> {
    return this.lineItems?.map {
        LineItem(
            it.quantity, it.title, it.price,it.id)
    } ?: emptyList()
}
fun OrderDetailsResponse.convertToLineItem(): List<LineItem> {
    return this.order?.lineItems?.map {
        LineItem(
            it.quantity, it.title, it.price,it.id)
    } ?: emptyList()
}
fun ProductResponse.convertToImage(): List<Image> {
    return this.images.map {
        Image(it.src)
    }
}





fun Product.convertProductToFavoriteEntity(customerId: Long): FavoriteEntity {
    return FavoriteEntity(
        id,
        customerId,
        productImages.map { ImageEntity(it.src) },
        productType,
        ImageEntity(productImage.src),
        title,
        productVariants.map { VariantEntity(
            it.id,it.price, it.productId, it.title,it.weightUnit)},
        productOptions.map { OptionEntity(it.values) },
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
                LineItems(product_id = it.productId.toString(), variant_id = it.variants[0].id)
            },
            CustomerDraftRequest(customerId)
        )
    )

}

fun List<FavoriteEntity>.convertFavoritesEntityToFavorites(): List<Favorite> {
    return map {
        Favorite(it.productId,it.image.src,it.title, it.variants[0].price!!)
    }

}

fun DraftOrderResponse.convertDraftOrderResponseToProductsIds(): List<Long> {
   return draft_order.line_items.map { it.product_id!! }
}

fun AllProductsResponse.convertAllProductsResponseToProductsIds(customerId: Long): List<FavoriteEntity> {
    return products.map {
        FavoriteEntity(
            it.id,
            customerId,
            it.images.convertImagesToImagesEntity() ,
            it.productType,
            ImageEntity(it.image.src.toString()),
            it.title,
            it.variants.map { v->
                VariantEntity(v.id,v.price,v.productId,v.title,v.weightUnit)
            } ,
            it.options.convertOptionsToOptionsEntity(),
            it.vendor,it.status)
    }
}

fun FavoriteEntity.convertFavoriteEntityToProduct():Product{
    return Product(productId,images.map { ProductImage(it.src) },
        productType, ProductImage(image.src),title,variants.map { ProductVariant(
            it.id?:0L,it.price.toString(), it.productId?:0L, it.title.toString(),it.weightUnit.toString()) },
    options.map { ProductOption(it.values) },vendor.toString(),status.toString())

}

fun DraftOrderResponse.convertToCartItems(): List<Cart> =
    this.draft_order.line_items.map { item ->
        Cart(
            quantity = item.quantity,
            title = item.title,
            price = item.price,
            productId = item.product_id,
            variantId = item.variant_id,
            variantTitle = item.variant_title,
            imageSrc = ""
        )
    }

fun Product.convertToLineItemRequest(): LineItems {
    return LineItems(product_id = this.id.toString(), variant_id = this.variants[0].id)
}

fun List<com.kh.mo.shopyapp.model.response.draft_order.LineItem>.convertToLineItemRequest(): List<LineItems> {
    return this.map {
        LineItems(variant_id = it.variant_id)
    }
}

fun List<LineItems>.convertToDraftOrderRequest(customerId: Long): DraftOrderRequest {
    return DraftOrderRequest(
        DraftOrderDetailsRequest(
            this.distinctBy { lineItems -> lineItems.variant_id }, CustomerDraftRequest(customerId)
        )
    )
}