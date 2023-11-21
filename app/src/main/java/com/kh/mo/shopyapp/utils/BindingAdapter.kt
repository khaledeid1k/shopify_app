package com.kh.mo.shopyapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlin.random.Random

@BindingAdapter("setDollarSign")
fun TextView.setDollarSign(price: String?) {
    this.text="$${price?.toDouble()?.toInt()}"
}


@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(this).load(url)
        .into(this)
}

@BindingAdapter("setRandomRate")
fun TextView.setRandomRate(t:String) {
    this.text= String.format("%.1f", Random.nextDouble(1.0, 5.0))
}