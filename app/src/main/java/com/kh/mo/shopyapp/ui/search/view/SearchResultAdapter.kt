package com.kh.mo.shopyapp.ui.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemSearchBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.ui.base.BaseDataDiffUtil

class SearchResultAdapter (private val onClickSearchItem: ProductsSearchListener):
    RecyclerView.Adapter<SearchResultAdapter.SearchResultAdapterVH>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapterVH {

        return SearchResultAdapterVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_search,
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: SearchResultAdapterVH, position: Int) {
        val currentItem = products[position]
        holder.onBind(currentItem,onClickSearchItem)

    }



    inner class SearchResultAdapterVH( val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: Product,onClickSearchItem:ProductsSearchListener) {
            binding.apply {
                item = currentItem
                listener=onClickSearchItem
            }
        }



    }


    override fun getItemCount()=products.size
    fun setItems(newItems: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(products, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> products[oldItemPosition].id == newItems[newItemPosition].id },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> products[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        products = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    interface ProductsSearchListener {
        fun onClickSearchItem(product: Product)
    }
}