package com.example.ecommerce.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ecommerce.Customer.customerFragLogin
import com.example.ecommerce.Shopkeeper.shopkeeperFragLogin

class pagerAdapterLogin(fm: FragmentManager?, private val numOfTabs: Int) : FragmentPagerAdapter(
    fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> customerFragLogin()
            1 -> shopkeeperFragLogin()
            else -> {
                throw IllegalStateException("position $position is invalid for this viewpager")
            }
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}