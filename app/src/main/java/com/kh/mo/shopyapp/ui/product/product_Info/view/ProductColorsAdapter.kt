package com.kh.mo.shopyapp.ui.product.product_Info.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemSelectColorBinding

class ProductColorsAdapter(
    private val colors: List<String?>?
) :
    RecyclerView.Adapter<ProductColorsAdapter.ViewHolder>() {
    private lateinit var binding: ItemSelectColorBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemSelectColorBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount() = colors?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (colors?.get(position)) {
            "white" -> binding.colorText.load(R.drawable.white)
            "black" -> binding.colorText.load(R.drawable.black)
            "blue" -> binding.colorText.load(R.drawable.blue)
            "red" -> binding.colorText.load(R.drawable.red)
            "gray" -> binding.colorText.load(R.drawable.gray)
            "yellow" -> binding.colorText.load(R.drawable.yellow)
            "beige" -> binding.colorText.load(R.drawable.beige)
            "light_brown" -> binding.colorText.load(R.drawable.light_brown)
            "burgandy" -> binding.colorText.load(R.drawable.burgandy)
        }


    }

    class ViewHolder(binding: ItemSelectColorBinding) : RecyclerView.ViewHolder(binding.root)
}