package com.kh.mo.shopyapp.ui.product.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.kh.mo.shopyapp.databinding.ItemProductImageBinding
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.ui.allproducts.ProductImage

class ProductImagesAdapter(
    private val images: List<ProductImage?>?
) :
    RecyclerView.Adapter<ProductImagesAdapter.ViewHolder>() {
    private lateinit var binding: ItemProductImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemProductImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = images?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = images?.get(position)
        binding.productImage.load(imageItem?.src)
    }

    class ViewHolder(binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root)
}