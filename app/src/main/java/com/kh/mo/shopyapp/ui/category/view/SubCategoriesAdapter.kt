package com.kh.mo.shopyapp.ui.category.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemCategoryBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Products

class SubCategoriesAdapter(private var context: Context):
    ListAdapter<Products, SubCategoriesAdapter.SubCategoriseVH>(RecyclerDiffUtilSubCategoriesItem()) {
    private lateinit var binding: ItemCategoryBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoriseVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return SubCategoriseVH(binding)
    }

    override fun onBindViewHolder(holder: SubCategoriseVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
    }

    inner class SubCategoriseVH(private var binding: ItemCategoryBinding): ViewHolder(binding.root){
        fun onBind(currentItem: Products) {
            binding.apply {
                tvNameMainCategory.text = currentItem.productType
//                Glide.with(context)
//                    .load(currentItem.image?.src)
//                    .placeholder(R.drawable.placeholder_products)
//                    .into(imageBrandItem)
//                Log.i("HomeFragment", currentItem.image?.src.toString())
            }

        }
    }
}
class RecyclerDiffUtilSubCategoriesItem : DiffUtil.ItemCallback<Products>() {
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