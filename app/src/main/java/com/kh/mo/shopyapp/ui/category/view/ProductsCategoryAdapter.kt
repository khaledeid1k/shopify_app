package com.kh.mo.shopyapp.ui.category.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemProductBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product

class SubCategoriesAdapter(private var context: Context,val onclickFavorite:(position:Int)->Unit):
    ListAdapter<Product, SubCategoriesAdapter.SubCategoriseVH>(RecyclerDiffUtilSubCategoriesItem()) {
    private lateinit var binding: ItemProductBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoriseVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemProductBinding.inflate(inflater, parent, false)
        return SubCategoriseVH(binding)
    }

    override fun onBindViewHolder(holder: SubCategoriseVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)

        binding.imageFav.setOnClickListener {
            onclickFavorite(position)
        }
    }

    inner class SubCategoriseVH(private var binding: ItemProductBinding): ViewHolder(binding.root){
        fun onBind(currentItem: Product) {
            binding.apply {
                tvNameProductItem.text = currentItem.title
                Glide.with(context)
                    .load(currentItem.image?.src)
                    .placeholder(R.drawable.placeholder_products)
                    .into(imageProductItem)
                tvPriceProductItem.text=currentItem.variants?.get(0)?.price.toString()
                Log.i("HomeFragment", currentItem.image?.src.toString())

            }

        }
    }
}
class RecyclerDiffUtilSubCategoriesItem : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product, newItem: Product
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Product, newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}