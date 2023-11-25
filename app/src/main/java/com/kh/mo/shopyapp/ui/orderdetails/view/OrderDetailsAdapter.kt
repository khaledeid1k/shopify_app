package com.kh.mo.shopyapp.ui.orderdetails.view

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
import com.kh.mo.shopyapp.model.ui.order.LineItem
import com.kh.mo.shopyapp.model.ui.order.Order
import com.kh.mo.shopyapp.ui.order.view.OrderAdapter

class OrderDetailsAdapter(var context: Context) :
    ListAdapter<LineItem, OrderDetailsAdapter.OrdersVH>(RecyclerDiffUtilOrdersItem()) {
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


        fun onBind(currentItem: LineItem) {
            Log.i("OrderFragmentAdapter","test")
            binding.apply {

//                tvProductNameOrder.text = currentItem.id.toString()
//                tvProductDateOrder.text=currentItem.customerResponse?.createdAt
//                tvProductPriceOrder.text=currentItem.subtotalPrice+"EGP"
//                tvProductSizeOrder.text=currentItem.lineItems?.get(0)?.quantity.toString()+"x"
//                binding.imageProductOrder.setImageResource(R.drawable.placeholder_products)
                tvProductNameOrder.text = currentItem.title
                //tvProductDateOrder.text=currentItem.
                tvProductPriceOrder.text=currentItem.price+"EGP"
                tvProductSizeOrder.text="${currentItem.quantity}x"

                binding.imageProductOrder.setImageResource(R.drawable.placeholder_products)
                //Glide.with(context).load(currentItem.)

            }

        }
    }
}

class RecyclerDiffUtilOrdersItem : DiffUtil.ItemCallback<LineItem>() {
    override fun areItemsTheSame(
        oldItem: LineItem, newItem: LineItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: LineItem, newItem: LineItem
    ): Boolean {
        return oldItem == newItem
    }
}