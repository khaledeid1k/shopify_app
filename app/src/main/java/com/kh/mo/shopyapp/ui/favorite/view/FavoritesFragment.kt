package com.kh.mo.shopyapp.ui.favorite.view

import android.os.Bundle
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentFavoritesBinding
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.favorite.viewmodel.FavoritesViewModel


class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {

    override val layoutIdFragment=R.layout.fragment_favorites

    override fun getViewModelClass()=FavoritesViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favouriteText.setOnClickListener {

        }
    }


}