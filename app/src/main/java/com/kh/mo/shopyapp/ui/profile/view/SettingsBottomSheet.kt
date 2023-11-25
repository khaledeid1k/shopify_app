package com.kh.mo.shopyapp.ui.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.BottomSheetLayoutBinding
import com.kh.mo.shopyapp.model.ui.CurrencySettingModel

class SettingsBottomSheet(
    private val itemList: List<CurrencySettingModel>,
    private val onItemClickListener: (CurrencySettingModel) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", "onViewCreated: ")
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.bottomSheetRV.addItemDecoration(dividerItemDecoration)
        val adapter = SettingsBottomSheetAdapter(itemList, onItemClickListener)
        binding.bottomSheetRV.adapter = adapter
    }
}