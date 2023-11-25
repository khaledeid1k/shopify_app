package com.kh.mo.shopyapp.ui.favorite.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentFavoritesBinding
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.favorite.viewmodel.FavoritesViewModel
import kotlinx.coroutines.launch


class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    private val favoritesAdapter = FavoritesAdapter()
    override val layoutIdFragment=R.layout.fragment_favorites

    override fun getViewModelClass()=FavoritesViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti()
        getAllFavorites()
    }

    fun inti(){
        binding.favoritesList.adapter=favoritesAdapter
    }

   private fun getAllFavorites(){
        lifecycleScope.launch {
        viewModel.favorites.collect{
            favoritesAdapter.setItems(it)

        }
    }
    }


}