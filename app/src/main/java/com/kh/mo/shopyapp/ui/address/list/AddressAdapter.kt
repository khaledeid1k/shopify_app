package com.kh.mo.shopyapp.ui.address.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.databinding.ItemLocationBinding
import com.kh.mo.shopyapp.model.ui.Address

class AddressAdapter(private val context: Context, private val listener: (Address) -> Unit) :
    ListAdapter<Address, AddressAdapter.ViewHolder>(AddressDiffUtils()) {
    lateinit var binding: ItemLocationBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemLocationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        binding.apply {
            addressNameValueTxtV.text = item.name
            isDefaultAddressTxtV.visibility = if(item.default == true) View.VISIBLE else View.GONE
            addressValueTxtV.text =
                "${item.city} ${item.state} ${item.country}\n${item.phone}\n${item.address}"
            addressItemCard.setOnClickListener { listener(item) }
        }
    }

    class ViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)
}

class AddressDiffUtils : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(
        oldItem: Address, newItem: Address
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Address, newItem: Address
    ): Boolean {
        return oldItem == newItem
    }
}