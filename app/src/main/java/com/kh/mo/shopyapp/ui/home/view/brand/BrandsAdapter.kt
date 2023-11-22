package com.kh.mo.shopyapp.home.view


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemBrandBinding
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection


class BrandsAdapter(var context: Context,private val onClick:(SmartCollection)->Unit) :
    ListAdapter<SmartCollection, BrandsAdapter.BrandsVH>(RecyclerDiffUtilBrandsItem()) {
    private lateinit var binding: ItemBrandBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsVH {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemBrandBinding.inflate(inflater, parent, false)
        return BrandsVH(binding)
    }

    override fun onBindViewHolder(holder: BrandsVH, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
        holder.itemView.setOnClickListener{
            onClick(currentItem)
            //Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_brandProductsFragment)
        }
    }

    inner class BrandsVH(var binding: ItemBrandBinding) : ViewHolder(binding.root) {

        fun onBind(currentItem: SmartCollection) {
            binding.apply {
                tvBrandNameItem.text = currentItem.title
                Glide.with(context)
                    .load(currentItem.image?.src)
                    .placeholder(R.drawable.placeholder_products)
                    .into(imageBrandItem)
                Log.i("HomeFragment", currentItem.image?.src.toString())
            }

        }
    }


}

class RecyclerDiffUtilBrandsItem : DiffUtil.ItemCallback<SmartCollection>() {
    override fun areItemsTheSame(
        oldItem: SmartCollection, newItem: SmartCollection
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: SmartCollection, newItem: SmartCollection
    ): Boolean {
        return oldItem == newItem
    }
}
