package com.kh.mo.shopyapp.ui.order.view

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
import com.kh.mo.shopyapp.model.ui.order.Image
import com.kh.mo.shopyapp.model.ui.order.Order

class OrderImageAdapter (var context: Context) :
    ListAdapter<Image, OrderImageAdapter.OrdersVH>(RecyclerDiffUtilImageOrdersItem()) {
    private lateinit var binding: ItemOrderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemOrderBinding.inflate(inflater, parent, false)
        Log.i("OrderFragmentAdapter","test1")
        return OrdersVH(binding)
    }

    override fun onBindViewHolder(holder: OrdersVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
        Log.i("OrderFragmentAdapter","test1")

    }

    inner class OrdersVH(var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(currentItem: Image) {
            Log.i("OrderFragmentAdapter","test")
            binding.apply {

                Glide.with(context)
                    .load(currentItem.src)
                    .placeholder(R.drawable.placeholder_products)
                    .into(imageProductOrder)

            }

        }
    }
}

class RecyclerDiffUtilImageOrdersItem : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(
        oldItem: Image, newItem: Image
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Image, newItem: Image
    ): Boolean {
        return oldItem == newItem
    }

}