package com.kh.mo.shopyapp.utils

import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
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

@BindingAdapter("isLogin")
fun CheckBox.checkIsLogin(isLogin: Boolean) {
     if(!isLogin) {
         setButtonDrawable(R.drawable.uncheck_favorite)
     }
}

@BindingAdapter("checkIsProductFavorite")
fun AppCompatButton.checkIsProductFavorite(favorite: Boolean) {
    text = if(favorite) {
        context.getString(R.string.favorite)
    }else{
        context.getString(R.string.add_to_favorite)
    }

}

