package com.kh.mo.shopyapp.ui.home.view.brand

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemOrderBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Products

class ProductsOfBrandsAdapter( var context: Context) :
ListAdapter<Products, ProductsOfBrandsAdapter.ProductsOfBrandVH>(RecyclerDiffUtilProductsBrandsItem()) {
    private lateinit var binding: ItemOrderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsOfBrandVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemOrderBinding.inflate(inflater, parent, false)
        return ProductsOfBrandVH(binding)
    }

    override fun onBindViewHolder(holder: ProductsOfBrandVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)

    }

    inner class ProductsOfBrandVH(var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(currentItem: Products) {
            binding.apply {
                tvProductNameOrder.text = currentItem.title
                Glide.with(context)
                    .load(currentItem.images.get(0).src)
                    .placeholder(R.drawable.placeholder_products)
                    .into(imageProductOrder)
                Log.i("HomeFragment", currentItem.images.get(0).src.toString())

                tvProductPriceOrder.text=currentItem.variants.get(0).price
            }

        }
    }


}

class RecyclerDiffUtilProductsBrandsItem : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(
        oldItem: Products, newItem: Products
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Products, newItem: Products
    ): Boolean {
        return oldItem == newItem
    }
}