package com.kh.mo.shopyapp.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.kh.mo.shopyapp.R
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

fun Double.roundTwoDecimals(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun createDialog(title:String="", message:String="", view: View?=null,
                 context: Context, sure:()->Unit, cancel:()->Unit){

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setView(view)
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                cancel()
                dialog.dismiss()

            }
            .setPositiveButton(context.getString(R.string.done)) { dialog, _ ->
                sure()
                dialog.dismiss()

            }
            .show()

}