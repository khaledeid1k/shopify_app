package com.kh.mo.shopyapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("EEEMMMddyyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    return sdf.format(calendar.time)
}