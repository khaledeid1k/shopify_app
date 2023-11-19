package com.kh.mo.shopyapp.ui.product

import android.os.Bundle
import android.telecom.Call.Details
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentProductBinding



class ProductFragment : Fragment() {
    lateinit var binding: FragmentProductBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        val viewpagger: ViewPager2 = binding.homeFragmentViewPager
        viewpagger.isSaveEnabled = false
        val adapter = MyViewPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(ProductInfoFragment(), getString(R.string.product))
        adapter.addFragment(ReviewsFragment(), getString(R.string.reviews))
        adapter.addFragment(DetailsFragment(), getString(R.string.details))
        //adapter.notifyDataSetChanged()
        viewpagger.adapter = adapter
        TabLayoutMediator(binding.homeFragmentTabs, viewpagger) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            viewpagger.setCurrentItem(tab.position, true)
        }.attach()
        return binding.root
    }

    class MyViewPagerAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(manager, lifecycle) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }


        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        fun getPageTitle(position: Int): CharSequence {
            return titleList[position]
        }


    }
}