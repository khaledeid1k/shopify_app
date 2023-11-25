package com.kh.mo.shopyapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import java.math.BigDecimal
import java.math.RoundingMode


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

fun Double.toUSD(usdToEgp: Double): Double {
    return this / usdToEgp
}

fun Double.roundTwoDecimals(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}