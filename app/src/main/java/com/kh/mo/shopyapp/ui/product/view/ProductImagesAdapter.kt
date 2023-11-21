package com.kh.mo.shopyapp.ui.product.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemProductImageBinding

class ProductImagesAdapter (
    private val context: Context,
    private val adsList: List<String>,
) :
    RecyclerView.Adapter<ProductImagesAdapter.ViewHolder>() {
    private lateinit var binding: ItemProductImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemProductImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = adsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adItem = adsList[position]
        Glide.with(context)
            .load(adItem)
            .placeholder(R.drawable.product_placeholder)
            .into(binding.productImage)
    }

    class ViewHolder(binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root)
}