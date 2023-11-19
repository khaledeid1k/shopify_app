package com.kh.mo.shopyapp.ui.home.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kh.mo.shopyapp.databinding.BrandItemBinding
import com.kh.mo.shopyapp.model.BrandResponse

class BrandsAdapter(var context: Context, var brandList: List<BrandResponse>):Adapter<BrandsAdapter.BrandsVH> (){
    private lateinit var binding: BrandItemBinding
    class BrandsVH( var binding:BrandItemBinding) :ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsVH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BrandsVH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}