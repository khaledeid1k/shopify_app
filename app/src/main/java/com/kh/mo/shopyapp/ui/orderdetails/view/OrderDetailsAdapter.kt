package com.kh.mo.shopyapp.ui.orderdetails.view

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemOrderBinding
import com.kh.mo.shopyapp.local.LocalSourceImp
import com.kh.mo.shopyapp.model.ui.orderdetails.LineItem
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSource
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OrderDetailsAdapter(var context: Context) :
    ListAdapter<LineItem, OrderDetailsAdapter.OrdersVH>(RecyclerDiffUtilOrdersItem()) {
    private lateinit var binding: ItemOrderBinding
    private val TAG = "TAG OrderDetailsAdapter"



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemOrderBinding.inflate(inflater, parent, false)
        return OrdersVH(binding)
    }

    override fun onBindViewHolder(holder: OrdersVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
    }

    inner class OrdersVH(var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: LineItem) {
            Log.i("OrderFragmentAdapter","test")
            binding.apply {
                tvProductNameOrder.text = currentItem.title
                //tvProductDateOrder.text=currentItem.
                tvProductPriceOrder.text=currentItem.price+"EGP"
                tvProductSizeOrder.text="${currentItem.quantity}x"
                Log.i(TAG,currentItem.productId.toString())

                CoroutineScope(Dispatchers.IO).launch {
                    RepoImp.getRepoImpInstance(RemoteSourceImp.getRemoteSourceImpInstance(),
                        LocalSourceImp.getLocalSourceImpInstance(context)).getImageOrders(currentItem.productId!!).collect {
                        when (it) {
                            is ApiState.Failure ->{}
                            is ApiState.Loading ->{}
                            is ApiState.Success -> {
                                withContext(Dispatchers.Main) {
                                    Glide.with(itemView.context)
                                        .load(it.data.images.get(0).src)
                                        .into(imageProductOrder)
                                }
                            }

                        }
                    }
                }

                //Glide.with(context).load(uri).into(binding.imageProductOrder)

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