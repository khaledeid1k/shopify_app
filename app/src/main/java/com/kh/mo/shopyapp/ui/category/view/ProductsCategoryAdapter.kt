package com.kh.mo.shopyapp.ui.category.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR.listener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemProductBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product

class ProductsCategoryAdapter(private val onclickFavorite:ProductsCategoryListener):
    ListAdapter<Product, ProductsCategoryAdapter.SubCategoriseVH>(ProductsDiffUtil()) {
    private lateinit var binding: ItemProductBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoriseVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemProductBinding.inflate(inflater, parent, false)
        return SubCategoriseVH(binding)
    }

    override fun onBindViewHolder(holder: SubCategoriseVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem,onclickFavorite)


        }



    inner class SubCategoriseVH( val binding: ItemProductBinding) :
        ViewHolder(binding.root) {
        fun onBind(currentItem: Product, productsCategoryListener: ProductsCategoryListener) {
            binding.apply {
                item = currentItem
                listener = productsCategoryListener
            }
        }



    }

    interface ProductsCategoryListener {

        fun onClickFavouriteIcon(product: Product)
    }


    class ProductsDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.isFavorite == newItem.isFavorite
        }

    }



}

