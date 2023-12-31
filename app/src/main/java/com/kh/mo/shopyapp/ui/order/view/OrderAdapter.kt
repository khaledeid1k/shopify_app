package com.kh.mo.shopyapp.ui.order.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemOrderBinding
import com.kh.mo.shopyapp.model.ui.orderdetails.Order


class OrderAdapter(var context: Context,private val onClick:(Long) -> Unit) :
    ListAdapter<Order, OrderAdapter.OrdersVH>(RecyclerDiffUtilOrdersItem()) {
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


        fun onBind(currentItem: Order) {
            Log.i("OrderFragmentAdapter","test")
            binding.apply {

                tvProductNameOrder.text = "#"+currentItem.orderNumber.toString()
                val date=currentItem.customer?.createdAt
                tvProductDateOrder.text=date?.substring(0,date.indexOf("T")).toString()
                tvProductPriceOrder.text=currentItem.subtotalPrice+currentItem.currency
                tvProductSizeOrder.text=currentItem.lineItems?.get(0)?.quantity.toString()+"x"
                binding.imageProductOrder.setImageResource(R.drawable.order_bag_resized)

            }
            itemView.setOnClickListener{
                onClick(currentItem.id!!)
            }

        }
    }
}

class RecyclerDiffUtilOrdersItem : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(
        oldItem: Order, newItem: Order
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Order, newItem: Order
    ): Boolean {
        return oldItem == newItem
    }
}