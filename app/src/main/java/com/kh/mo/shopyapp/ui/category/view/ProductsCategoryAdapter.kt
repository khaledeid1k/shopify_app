package com.kh.mo.shopyapp.ui.category.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR.listener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemProductBinding
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.ui.base.BaseDataDiffUtil
import com.kh.mo.shopyapp.ui.product.product_reviews.view.ReviewsAdapter

class ProductsCategoryAdapter(private val onclickFavorite:ProductsCategoryListener,
                              private   val onClickCategory:(Int)->Unit
):
    RecyclerView.Adapter<ProductsCategoryAdapter.SubCategoriseVH>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoriseVH {

        return SubCategoriseVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_product,
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: SubCategoriseVH, position: Int) {
        val currentItem = products[position]
        holder.onBind(currentItem,onclickFavorite)
       holder.itemView.setOnClickListener {
           onClickCategory(position)
       }

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


    override fun getItemCount()=products.size
    fun setItems(newItems: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(products, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> products[oldItemPosition].isFavorite == newItems[newItemPosition].isFavorite },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> products[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        products = newItems
        diffResult.dispatchUpdatesTo(this)
    }
    interface ProductsCategoryListener {

        fun onClickFavouriteIcon(product: Product)
    }

}

