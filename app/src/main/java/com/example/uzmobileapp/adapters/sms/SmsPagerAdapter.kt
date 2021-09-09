package com.example.uzmobileapp.adapters.sms

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobileapp.models.internet.InternetCategory
import com.example.uzmobileapp.models.sms.SmsCategory
import com.example.uzmobileapp.ui.daqiqa.DaqiqaAdapterFragment
import com.example.uzmobileapp.ui.internet.InternetAdapterFragment
import com.example.uzmobileapp.ui.sms.SmsAdapterFragment

class SmsPagerAdapter(
    var categoryList: List<SmsCategory>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return categoryList.size
    }

    var fraagments: ArrayList<SmsAdapterFragment> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        val fragment = SmsAdapterFragment.newInstance(categoryList[position])
        fraagments.add(fragment)
        return fragment
    }

}