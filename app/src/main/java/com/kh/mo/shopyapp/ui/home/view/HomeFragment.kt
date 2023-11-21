package com.kh.mo.shopyapp.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentHomeBinding
import com.kh.mo.shopyapp.home.view.BrandsAdapter
import com.kh.mo.shopyapp.model.ui.AdModel
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutIdFragment = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java
    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var mainCategoriesAdapter: MainCategoryAdapter
    private lateinit var adsAdapter: AdsAdapter
    private val listener: (AdModel) -> Unit = {
        viewModel.getCoupon(it)
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
            while (isActive) {
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
            viewModel.mainCategories.collect {
                mainCategoriesAdapter = MainCategoryAdapter(requireContext())
                //to drop the first item from response
                mainCategoriesAdapter.submitList(it.toData()?.drop(1))

                Log.i("HomeFragment", it.toData()?.get(0)?.image?.src.toString())
                binding.recyclerCategory.adapter = mainCategoriesAdapter
            }

        }
    }

    private fun getBrands() {
        lifecycleScope.launch {
            viewModel.barnds.collect {
                brandsAdapter = BrandsAdapter(requireContext()) {
                    val action = HomeFragmentDirections.actionHomeFragmentToBrandProductsFragment(
                        it.title!!,
                        it.image?.src!!
                    )
                    Navigation.findNavController(requireView()).navigate(action)
                }
                brandsAdapter.submitList(it.toData())

                Log.i("HomeFragment", it.toData()?.get(0)?.image?.src.toString())
                binding.recyclerBrands.adapter = brandsAdapter
            }
        }
    }
}