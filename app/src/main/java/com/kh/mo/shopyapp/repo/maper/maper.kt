package com.kh.mo.shopyapp.repo.maper

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection

fun BrandsResponse.convertToSmartCollection():List<SmartCollection>{

    return this.smartCollections?.map {

        SmartCollection(it?.title,it?.image)
    } ?: emptyList()

}