package com.kh.mo.shopyapp.repo.maper

import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.allproducts.Products
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection

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
