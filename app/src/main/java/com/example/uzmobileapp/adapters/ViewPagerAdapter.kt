package com.example.uzmobileapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobileapp.models.Tariflar
import com.example.uzmobileapp.models.sms.SmsCategory
import com.example.uzmobileapp.ui.sms.SmsAdapterFragment
import com.example.uzmobileapp.ui.tariflar.AboutTarifFragment

class ViewPagerAdapter(
    var list: List<Tariflar>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    var fraagments: ArrayList<AboutTarifFragment> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        val fragment = AboutTarifFragment.newInstance(list[position])
        fraagments.add(fragment)
        return fragment
    }

}