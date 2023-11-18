package com.kh.mo.shopyapp.model

data class BrandResponse(var smart_collections: List<Brands>)
data class Brands(val id:Long,var title:String,var image:Image)
data class Image(var src:String)