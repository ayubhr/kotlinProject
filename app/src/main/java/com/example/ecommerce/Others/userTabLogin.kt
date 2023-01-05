package com.example.ecommerce.Others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabItem
import androidx.viewpager.widget.ViewPager
import com.example.ecommerce.Adapter.pagerAdapterLogin
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener

class userTabLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_ui)
        val tabLayout = findViewById<TabLayout>(R.id.tabBarLogin)
        val customerTab = findViewById<TabItem>(R.id.customer_tab_login)
        val shopkeeperTab = findViewById<TabItem>(R.id.shopkeeper_tab_login)
        val viewPager = findViewById<ViewPager>(R.id.view_pager_login)
        val PagerAdapter = pagerAdapterLogin(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = PagerAdapter
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
    }
}