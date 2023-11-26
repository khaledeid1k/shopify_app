package com.kh.mo.shopyapp.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.databinding.ItemBottomSheetSettingsBinding
import com.kh.mo.shopyapp.model.ui.LanguageSettingModel

class LanguageBottomSheetAdapter(
    private val onItemClickListener: (LanguageSettingModel) -> Unit
) :
    ListAdapter<LanguageSettingModel, LanguageBottomSheetAdapter.ViewHolder>(LanguageDiffUtils()) {
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

class LanguageDiffUtils : DiffUtil.ItemCallback<LanguageSettingModel>() {
    override fun areItemsTheSame(
        oldItem: LanguageSettingModel, newItem: LanguageSettingModel
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: LanguageSettingModel, newItem: LanguageSettingModel
    ): Boolean {
        return oldItem == newItem
    }
}