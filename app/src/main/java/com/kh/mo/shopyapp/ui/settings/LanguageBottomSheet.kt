package com.kh.mo.shopyapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.BottomSheetLayoutBinding
import com.kh.mo.shopyapp.model.ui.LanguageSettingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LanguageBottomSheet (
    var languageList: List<LanguageSettingModel>,
    private val onItemClickListener: (LanguageSettingModel) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    private lateinit var adapter: LanguageBottomSheetAdapter
    val isListUpdated: MutableStateFlow<Boolean> = MutableStateFlow(false)

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

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.bottomSheetRV.addItemDecoration(dividerItemDecoration)
        adapter = LanguageBottomSheetAdapter(onItemClickListener)
        binding.bottomSheetRV.adapter = adapter
        adapter.submitList(languageList)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                isListUpdated.collectLatest {
                    //Log.i("TAG", "updated list: $currencyUnitList")
                    if (it) {
                        adapter.submitList(languageList)
                    }
                    isListUpdated.value = false
                }
            }
        }
    }
}