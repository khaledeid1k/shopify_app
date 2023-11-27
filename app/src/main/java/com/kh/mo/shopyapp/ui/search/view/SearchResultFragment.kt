package com.kh.mo.shopyapp.ui.search.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.room.util.query
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.DialogFilterBinding
import com.kh.mo.shopyapp.databinding.FragmentSearchResultBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.search.viewmodel.SearchResultViewModel
import com.kh.mo.shopyapp.utils.getText
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


class SearchResultFragment :BaseFragment<FragmentSearchResultBinding,SearchResultViewModel>(),
    SearchResultAdapter.ProductsSearchListener {

    lateinit var searchResultAdapter:SearchResultAdapter
    override val layoutIdFragment=R.layout.fragment_search_result
    private lateinit var filterDialog: Dialog
    private lateinit var filterBinding: DialogFilterBinding
    private var minPrice=0.0
    private var maxPrice=Double.MAX_VALUE
    private var productsList: MutableSet<Product> = mutableSetOf()
    private var query=""

    override fun getViewModelClass()=SearchResultViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
        setUp()
        getQuery()
        takeFilterPrice()
    }
    private fun initDialog() {
        filterDialog = Dialog(requireContext())
        filterDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        filterBinding = DialogFilterBinding.inflate(layoutInflater)
        filterDialog.setContentView(filterBinding.root)
    }
    private fun takeFilterPrice(){
        filterBinding.btnApplyFilter.setOnClickListener {
            if(filterBinding.etFrom.text.toString().isNotEmpty()){
                minPrice=filterBinding.etFrom.text.toString().toDouble()
            }
            if (filterBinding.etTo.text.toString().isNotEmpty()){
                maxPrice = filterBinding.etTo.text.toString().toDouble()

            }
            filterDialog.dismiss()
            productsList.clear()
            observeProducts(query)
        }

    }
    private fun setUp(){
        binding.filterSearch.setOnClickListener {
            filterDialog.show()
        }
    }


    private fun getQuery() {
        binding.searchText.getText {
            productsList.clear()
            query=it
            observeProducts(it)
        }

    }

    private fun observeProducts(query:String){
        lifecycleScope.launch {
            viewModel.products.collect {
                when(it){
                    is ApiState.Failure ->{}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {

                        it.data.asFlow().filter { product ->
                            product.title.contains(query, ignoreCase = true)
                                    && product.productVariants[0].price.toDouble() <= maxPrice
                                    && product.productVariants[0].price.toDouble() >= minPrice
                        } .collect{product->

                            if(query.isEmpty()) {
                                productsList.clear()

                            } else{
                                productsList.add(product)
                            }


                            addAdapterToSearch()
                        }



                    }
                }

            }
        }
    }





    private fun addAdapterToSearch(){
        searchResultAdapter = SearchResultAdapter(this)
        searchResultAdapter.setItems(productsList.toList().sortedBy{it.title})
        binding.searchResultList.adapter = searchResultAdapter
    }
    override fun onClickSearchItem(product: Product) {

    }
}
