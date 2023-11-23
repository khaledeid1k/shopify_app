package com.kh.mo.shopyapp.ui.address

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.databinding.ItemLocationBinding
import com.kh.mo.shopyapp.model.entity.AddressEntity

class AddressAdapter(private val context: Context) :
    ListAdapter<AddressEntity, AddressAdapter.ViewHolder>(AddressDiffUtils()) {
    lateinit var binding: ItemLocationBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemLocationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        binding.apply {
            addressNameValueTxtV.text = item.name
            isDefaultAddressTxtV.visibility = if(item.default) View.VISIBLE else View.GONE
            addressValueTxtV.text =
                "${item.city} ${item.state} ${item.country}\n${item.phone}\n${item.address}"
        }
    }

    class ViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)
}

class AddressDiffUtils : DiffUtil.ItemCallback<AddressEntity>() {
    override fun areItemsTheSame(
        oldItem: AddressEntity, newItem: AddressEntity
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: AddressEntity, newItem: AddressEntity
    ): Boolean {
        return oldItem == newItem
    }
}