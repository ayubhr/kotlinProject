package com.example.ecommerce.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ecommerce.Customer.customerFragRegister
import com.example.ecommerce.Shopkeeper.shopkeeperFragRegister

class pagerAdapterRegister(fm: FragmentManager?, private val numOfTabs: Int) : FragmentPagerAdapter(
    fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> customerFragRegister()
            1 -> shopkeeperFragRegister()
            else -> {
                throw IllegalStateException("position $position is invalid for this viewpager")
            }
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}