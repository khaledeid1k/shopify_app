package com.kh.mo.shopyapp.ui.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemCouponBinding
import com.kh.mo.shopyapp.model.ui.AdModel

class AdsAdapter(
    private val context: Context,
    private val adsList: List<AdModel>,
    private val listener: (AdModel) -> Unit
) :
    RecyclerView.Adapter<AdsAdapter.ViewHolder>() {
    private lateinit var binding: ItemCouponBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemCouponBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = adsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adItem = adsList[position]
        Glide.with(context)
            .load(adItem.imageUrl)
            .placeholder(R.drawable.product_placeholder)
            .into(binding.imageCoupon)
        binding.imageCoupon.setOnClickListener { listener(adItem) }
    }

    class ViewHolder(binding: ItemCouponBinding) : RecyclerView.ViewHolder(binding.root)
}