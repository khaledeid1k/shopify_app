package com.kh.mo.shopyapp.repo.maper

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.ProductsOfSpecificBrandResponse
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.model.ui.productsofbrand.Product

fun BrandsResponse.convertToSmartCollection():List<SmartCollection>{

    return this.smartCollections?.map {

        SmartCollection(it?.title,it?.image)
    } ?: emptyList()

}

fun MainCategoryResponse.convertToCustomCollection():List<CustomCollection>{

    return this.customCollections?.map {

        CustomCollection(it?.image,it?.title)
    } ?: emptyList()

}

fun ProductsOfSpecificBrandResponse.convertToProduct():List<Product>{

    return this.products?.map {

        Product(it?.images,it?.title,it?.variants,it?.options)
    } ?: emptyList()

}
