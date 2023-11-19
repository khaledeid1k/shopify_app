package com.kh.mo.shopyapp.ui.base

import androidx.recyclerview.widget.DiffUtil


class BaseDataDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val checkItemsTheSame: (oldItemPosition: Int, newItemPosition: Int)-> Boolean,
    private val checkContentsTheSame:(oldItemPosition: Int, newItemPosition: Int)-> Boolean
) : DiffUtil.Callback(){


    override fun getOldListSize()=oldList.size


    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkItemsTheSame(oldItemPosition, newItemPosition)

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkContentsTheSame(oldItemPosition, newItemPosition)

    }

}

