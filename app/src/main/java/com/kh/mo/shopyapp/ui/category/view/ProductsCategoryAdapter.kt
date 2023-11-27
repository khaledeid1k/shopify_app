package com.kh.mo.shopyapp.ui.category.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemProductBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.ui.base.BaseDataDiffUtil

class ProductsCategoryAdapter(
    private val onclickFavorite: ProductsCategoryListener,
    private val onClickCart: (Product) -> Unit,
    private val onClickCategory: (Int) -> Unit
) :
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
        holder.onBind(currentItem, onclickFavorite)
        holder.itemView.setOnClickListener {
            onClickCategory(position)
        }
        holder.binding.imageCart.setOnClickListener { onClickCart(currentItem) }
    }


    inner class SubCategoriseVH(val binding: ItemProductBinding) :
        ViewHolder(binding.root) {
        fun onBind(currentItem: Product, productsCategoryListener: ProductsCategoryListener) {
            binding.apply {
                item = currentItem
                listener = productsCategoryListener
            }
        }


    }


    override fun getItemCount() = products.size
    fun setItems(newItems: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(products, newItems,
                checkItemsTheSame = { oldItemPosition, newItemPosition -> products[oldItemPosition].isFavorite == newItems[newItemPosition].isFavorite },
                checkContentsTheSame = { oldItemPosition, newItemPosition -> products[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        products = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    interface ProductsCategoryListener {

        fun onClickFavouriteIcon(product: Product)
    }

}

