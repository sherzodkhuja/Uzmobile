package com.example.uzmobileapp.adapters.daqiqa

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobileapp.models.daqiqa.DaqiqaCategory
import com.example.uzmobileapp.models.internet.InternetCategory
import com.example.uzmobileapp.ui.daqiqa.DaqiqaAdapterFragment
import com.example.uzmobileapp.ui.internet.InternetAdapterFragment

class DaqiqaPagerAdapter(
    var categoryList: List<DaqiqaCategory>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {

    var fraagments: ArrayList<DaqiqaAdapterFragment> = ArrayList()

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DaqiqaAdapterFragment.newInstance(categoryList[position])
        fraagments.add(fragment)
        return fragment
    }

}