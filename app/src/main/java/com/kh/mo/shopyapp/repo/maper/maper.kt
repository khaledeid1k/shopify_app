package com.kh.mo.shopyapp.repo.maper

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection

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