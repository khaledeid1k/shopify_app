package com.kh.mo.shopyapp.ui.home.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.MainActivity
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentHomeBinding
import com.kh.mo.shopyapp.home.view.BrandsAdapter
import com.kh.mo.shopyapp.model.ui.AdModel
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val TAG = "HomeFragment"
    override val layoutIdFragment = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java
    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var mainCategoriesAdapter: MainCategoryAdapter
    private lateinit var adsAdapter: AdsAdapter
    private val listener: (AdModel) -> Unit = {
        viewModel.getCoupon(it)
        observeCouponState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllBrands()
        viewModel.getMainCategories()
        getBrands()
        getMainCategories()

        val adsList = listOf(
            AdModel(
                "https://drive.google.com/uc?export=download&id=1nmCztDLfwa7Kkw7-yz8tJEvqUi6oKA4M",
                "1402684080412",
                "17996919505180"
            ),
            AdModel(
                "https://drive.google.com/uc?export=download&id=1zAUMAjtzIXoF475iIHL8zEdfyY9LuyXh",
                "1402721403164",
                "18006657990940"
            ),
            AdModel(
                "https://drive.google.com/uc?export=download&id=1g7MDyebKasgGHrI1vgewTLhT-NO37ePW",
                "1402721534236",
                "18006657466652"
            )
        )
        adsAdapter = AdsAdapter(requireContext(), adsList, listener)
        binding.apply {
            couponPager.adapter = adsAdapter
            dotsIndicator.setViewPager2(couponPager)
        }

        val totalPages = adsList.size
        lifecycleScope.launch(Dispatchers.Main) {
            repeat(adsList.size * 2) {
                binding.couponPager.apply {
                    delay(3000)
                    binding.couponPager.apply {
                        if (currentItem + 1 > totalPages - 1) {
                            currentItem = 0
                        } else {
                            currentItem++
                        }
                    }
                }
            }
        }
    }

    private fun getMainCategories() {
        lifecycleScope.launch {
            viewModel.mainCategories.collect { category ->
                mainCategoriesAdapter = MainCategoryAdapter(requireContext()) {
                    //  Log.i("sssss",category.toData()!!.get(category.toData()!!.lastIndexOf(it)).title)
                    val title = category.toData()?.get(category.toData()!!.lastIndexOf(it))?.title!!
                    val collectionId =
                        category.toData()!!.get(category.toData()!!.lastIndexOf(it)).id!!
                    val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                        title,
                        collectionId
                    )
                    Navigation.findNavController(requireView()).navigate(action)


                }
                //to drop the first item from response
                mainCategoriesAdapter.submitList(category.toData()?.drop(1))

                Log.i("HomeFragment", category.toData()?.get(0)?.image?.src.toString())
                binding.recyclerCategory.adapter = mainCategoriesAdapter
            }

        }
    }

    private fun getBrands() {
        lifecycleScope.launch {
            viewModel.brands.collect {
                brandsAdapter = BrandsAdapter(requireContext()) {collection ->
                    val action = HomeFragmentDirections.actionHomeFragmentToBrandProductsFragment(
                        collection.title!!,
                        collection.image?.src!!
                    )
                    Navigation.findNavController(requireView()).navigate(action)
                }
                brandsAdapter.submitList(it.toData())

                Log.i("HomeFragment", it.toData()?.get(0)?.image?.src.toString())
                binding.recyclerBrands.adapter = brandsAdapter
            }
        }
    }

    private fun observeCouponState() {
        collectLatestFlowOnLifecycle(viewModel.couponState) {
            when (it) {
                is ApiState.Failure -> Log.i(TAG, "getCoupon: ${it.msg}")
                ApiState.Loading -> Log.i(TAG, "getCoupon: $it")
                is ApiState.Success -> {
                    Log.i(TAG, "getCoupon: ${it.data.discountCode?.code}")
                    it.data.discountCode?.code?.let { it1 -> copyDiscountCodeToClipboard(it1) }
                    (requireActivity() as MainActivity).copiedCoupon = it.data
                }
            }
        }
    }

    private fun copyDiscountCodeToClipboard(code: String) {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Copied Text", code)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), "code $code copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}