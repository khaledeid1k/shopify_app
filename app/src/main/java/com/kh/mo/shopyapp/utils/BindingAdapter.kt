package com.kh.mo.shopyapp.utils

import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import kotlin.random.Random



@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(this).load(url)
        .into(this)
}

@BindingAdapter("setRandomRate")
fun TextView.setRandomRate(t:String) {
    this.text= String.format("%.1f", Random.nextDouble(1.0, 5.0))
}
@BindingAdapter("isFavorite")
fun CheckBox.setFavoriteImage(isFavorite: Boolean) {
    isChecked = isFavorite
}
//@BindingAdapter(value = ["app:adapterImagesOfProduct"])
//fun ViewPager2.adapterImagesOfProduct(images: List<ImageResponse?>?) {
//    images?.let {
//        this.adapter = ProductImagesAdapter(it)
//    }
//
//}

