package com.kh.mo.shopyapp.ui.product.product_Info.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemCouponBinding
import com.kh.mo.shopyapp.databinding.ItemSelectSizeBinding
import com.kh.mo.shopyapp.ui.home.view.AdsAdapter

class ProductSizeAdapter (
    private val sizes: List<String?>?
) :
    RecyclerView.Adapter<ProductSizeAdapter.ViewHolder>() {
    private lateinit var binding: ItemSelectSizeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemSelectSizeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount() = sizes?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val size= sizes?.get(position)
        binding.sizeText.text=size
    }

    class ViewHolder(binding: ItemSelectSizeBinding) : RecyclerView.ViewHolder(binding.root)
}