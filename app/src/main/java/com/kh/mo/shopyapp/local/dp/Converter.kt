package com.kh.mo.shopyapp.local.dp

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kh.mo.shopyapp.model.entity.ImageEntity
import com.kh.mo.shopyapp.model.entity.OptionEntity
import com.kh.mo.shopyapp.model.entity.VariantEntity

class Converter {
    @TypeConverter
    fun fromImagesList(images: List<ImageEntity>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImagesList(imagesJson: String): List<ImageEntity> {
        val listType = object : TypeToken<List<ImageEntity>>() {}.type
        return Gson().fromJson(imagesJson, listType)
    }

    @TypeConverter
    fun fromVariantsList(variants: List<VariantEntity>): String {
        return Gson().toJson(variants)
    }

    @TypeConverter
    fun toVariantsList(variantsJson: String): List<VariantEntity> {
        val listType = object : TypeToken<List<VariantEntity>>() {}.type
        return Gson().fromJson(variantsJson, listType)
    }

    @TypeConverter
    fun fromOptionsList(options: List<OptionEntity>): String {
        return Gson().toJson(options)
    }

    @TypeConverter
    fun toOptionsList(optionsJson: String): List<OptionEntity> {
        val listType = object : TypeToken<List<OptionEntity>>() {}.type
        return Gson().fromJson(optionsJson, listType)
    }

    @TypeConverter
    fun fromImageEntity(image: ImageEntity): String {
        return Gson().toJson(image)
    }

    @TypeConverter
    fun toImageEntity(imageJson: String): ImageEntity {
        return Gson().fromJson(imageJson, ImageEntity::class.java)
    }
}