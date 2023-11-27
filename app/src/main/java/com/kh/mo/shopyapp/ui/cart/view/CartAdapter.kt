package com.kh.mo.shopyapp.ui.cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemCartBinding
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.utils.Constants


class CartAdapter(
    private val context: Context,
    private val listener: (Cart, String) -> Unit
) :
ListAdapter<Cart, CartAdapter.ViewHolder>(CartDiffUtils()) {
    lateinit var binding: ItemCartBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemCartBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        binding.apply {
            cartItemTitleTxtV.text = item.title
            cartItemVariantTxtV.text = item.variantTitle
            cartItemQuantityTxtV.text = item.quantity.toString()
            cartItemPriceTxtV.text = item.price
            Glide.with(context)
                .load(item.imageSrc)
                .placeholder(R.drawable.product_placeholder)
                .into(cartItemImgV)
            cartItemDeleteIC.setOnClickListener { listener(item, Constants.ACTION_DELETE) }
            cartItemAddBtn.setOnClickListener { listener(item, Constants.ACTION_ADD) }
            cartItemSubBtn.setOnClickListener { listener(item, Constants.ACTION_SUB) }
        }
    }

    class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)
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