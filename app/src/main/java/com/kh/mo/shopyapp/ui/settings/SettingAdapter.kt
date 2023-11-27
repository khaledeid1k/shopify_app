package com.kh.mo.shopyapp.ui.settings

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemSettingsBinding
import com.kh.mo.shopyapp.model.ui.SettingsModel

class SettingAdapter(
    private val settingList: List<SettingsModel>,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {
    private lateinit var binding: ItemSettingsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val inflater: LayoutInflater = parent
            .context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemSettingsBinding.inflate(inflater, parent, false)
        return SettingViewHolder(binding)
    }

    override fun getItemCount() = settingList.size

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val settingItem = settingList[position]
        binding.apply {
            settingTitleTxt.text = settingItem.title
            settingIcon.setImageResource(settingItem.icon)
            if (position == 5)
                binding.settingCard.setCardBackgroundColor(Color.RED)
            settingCard.setOnClickListener {
                listener(position)
            }
        }
    }

    class SettingViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)
}