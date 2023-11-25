package com.kh.mo.shopyapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("EEEMMMddyyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    return sdf.format(calendar.time)
}

fun formatCurrentDate(date: Date): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    return sdf.format(date)
}

fun parseDate(storedDate: String): Date? {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    return sdf.parse(storedDate)
}

fun isHoursPassed(storedDate: Date?): Boolean {
    storedDate ?: return false
    val currentTimeMillis = System.currentTimeMillis()
    val storedTimeMillis = storedDate.time
    val timeDifference = currentTimeMillis - storedTimeMillis
    println("current date: ${formatCurrentDate(Date(currentTimeMillis))}")
    return timeDifference >= 1 * 60 * 60 * 1000
}

fun main() {
    val storedDateString = "2023-11-23 16:5:00"
    val storedDate = parseDate(storedDateString)
    println(isHoursPassed(storedDate))
}