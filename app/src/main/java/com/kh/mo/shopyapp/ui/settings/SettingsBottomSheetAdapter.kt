package com.kh.mo.shopyapp.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.databinding.ItemBottomSheetSettingsBinding
import com.kh.mo.shopyapp.model.ui.CurrencySettingModel

class SettingsBottomSheetAdapter(
    private val onItemClickListener: (CurrencySettingModel) -> Unit
) :
    ListAdapter<CurrencySettingModel, SettingsBottomSheetAdapter.ViewHolder>(CurrencyDiffUtils()) {
    private lateinit var binding: ItemBottomSheetSettingsBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemBottomSheetSettingsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        binding.apply {
            settingTitleTxt.text = item.title
            checkIC.visibility = if (item.isThePreference) View.VISIBLE else View.GONE
            settingCard.setOnClickListener { onItemClickListener(item) }
        }
    }

    class ViewHolder(binding: ItemBottomSheetSettingsBinding) :
        RecyclerView.ViewHolder(binding.root)
}

class CurrencyDiffUtils : DiffUtil.ItemCallback<CurrencySettingModel>() {
    override fun areItemsTheSame(
        oldItem: CurrencySettingModel, newItem: CurrencySettingModel
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: CurrencySettingModel, newItem: CurrencySettingModel
    ): Boolean {
        return oldItem == newItem
    }
}