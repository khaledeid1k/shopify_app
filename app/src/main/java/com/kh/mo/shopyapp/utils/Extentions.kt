package com.kh.mo.shopyapp.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun TextInputEditText.getText(textValue: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence,
            i: Int,
            i1: Int,
            i2: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            i: Int,
            i1: Int,
            i2: Int
        ) {

            textValue(charSequence.toString().trim())
        }

        override fun afterTextChanged(editable: Editable) {}
    })
}

fun Double.toEGP(toEGPRate: Double): Double {
    return this * toEGPRate
}

fun Double.toGBP(toGBPRate: Double): Double {
    return this * toGBPRate
}

fun Double.toEUR(toEURRate: Double): Double {
    return this * toEURRate
}

fun main() {
    val sdf = SimpleDateFormat("EEEMMMddyyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    println(sdf.format(calendar.time) == "TueNov212023")
}