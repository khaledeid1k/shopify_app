package com.kh.mo.shopyapp.model.ui

import android.graphics.Color

data class SettingsModel(
    val title: String,
    val icon: Int,
    val titleColor: Int=Color.BLACK,
    val iconGoColor: Int=Color.BLACK
)