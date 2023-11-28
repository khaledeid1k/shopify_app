package com.kh.mo.shopyapp.ui.checkout.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemCheckoutBinding
import com.kh.mo.shopyapp.model.ui.Cart

class CheckoutAdapter(private val context: Context) :
    ListAdapter<Cart, CheckoutAdapter.ViewHolder>(CartDiffUtils()) {
    lateinit var binding: ItemCheckoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemCheckoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        binding.apply {
            checkoutItemTitleTxtV.text = item.title
            checkoutItemVariantTxtV.text = item.variantTitle
            checkoutItemQuantityTxtV.text = item.quantity.toString()
            checkoutItemPriceTxtV.text = item.price
            Glide.with(context)
                .load(item.imageSrc)
                .placeholder(R.drawable.product_placeholder)
                .into(checkoutItemImgV)
        }
    }

    class ViewHolder(val binding: ItemCheckoutBinding) : RecyclerView.ViewHolder(binding.root)
}

class CartDiffUtils : DiffUtil.ItemCallback<Cart>() {
    override fun areItemsTheSame(
        oldItem: Cart, newItem: Cart
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Cart, newItem: Cart
    ): Boolean {
        return oldItem == newItem
    }
}