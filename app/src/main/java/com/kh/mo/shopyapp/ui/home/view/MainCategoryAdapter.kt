package com.kh.mo.shopyapp.ui.home.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemBrandBinding
import com.kh.mo.shopyapp.databinding.ItemCategoryBinding
import com.kh.mo.shopyapp.home.view.BrandsAdapter
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection

class MainCategoryAdapter(var context: Context,private val onClick:(CustomCollection)->Unit) :
    ListAdapter<CustomCollection, MainCategoryAdapter.CategoryVH>(RecyclerDiffUtilCategoryItem()) {
    private lateinit var binding: ItemCategoryBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
        holder.itemView.setOnClickListener{
            onClick(currentItem)
        }
    }


    inner class CategoryVH(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(currentItem: CustomCollection) {
            binding.apply {
                tvNameMainCategory.text = currentItem.title
                Glide.with(context)
                    .load(currentItem.image?.src)
                    .placeholder(R.drawable.placeholder_products)
                    .into(imageCategoryItem)
                Log.i("HomeFragment", currentItem.image?.src.toString())


            }
        }
    }
}


    class RecyclerDiffUtilCategoryItem : DiffUtil.ItemCallback<CustomCollection>() {
        override fun areItemsTheSame(
            oldItem: CustomCollection,
            newItem: CustomCollection
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CustomCollection,
            newItem: CustomCollection
        ): Boolean {
            return oldItem == newItem
        }
    }

