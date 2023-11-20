package com.kh.mo.shopyapp.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText


fun TextInputEditText.getText(textValue:(text:String)->Unit) {
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