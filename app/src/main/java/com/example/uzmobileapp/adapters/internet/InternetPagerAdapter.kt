package com.example.uzmobileapp.adapters.internet

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobileapp.models.internet.InternetCategory
import com.example.uzmobileapp.ui.daqiqa.DaqiqaAdapterFragment
import com.example.uzmobileapp.ui.internet.InternetAdapterFragment

class InternetPagerAdapter(
    var categoryList: List<InternetCategory>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return categoryList.size
    }

    var fraagments: ArrayList<InternetAdapterFragment> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        val fragment = InternetAdapterFragment.newInstance(categoryList[position])
        fraagments.add(fragment)
        return fragment
    }

}