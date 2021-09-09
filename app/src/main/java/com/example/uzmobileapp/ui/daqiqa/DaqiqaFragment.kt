package com.example.uzmobileapp.ui.daqiqa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.adapters.daqiqa.DaqiqaPagerAdapter
import com.example.uzmobileapp.adapters.ussd.UssdAdapter
import com.example.uzmobileapp.databinding.FragmentDaqiqaBinding
import com.example.uzmobileapp.databinding.ItemTabSelectedBinding
import com.example.uzmobileapp.models.Ussd
import com.example.uzmobileapp.models.daqiqa.Daqiqa
import com.example.uzmobileapp.models.daqiqa.DaqiqaCategory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DaqiqaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DaqiqaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentDaqiqaBinding
    lateinit var daqiqaCategoryList: ArrayList<DaqiqaCategory>

    lateinit var daqiqaPagerAdapter: DaqiqaPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaqiqaBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Daqiqalar"


        getData()
        daqiqaPagerAdapter = DaqiqaPagerAdapter(daqiqaCategoryList, requireActivity())
        binding.viewPager2.adapter = daqiqaPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            setTabs(tab, position)
        }.attach()


        binding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabView = tab!!.customView
                var textView = tabView!!.findViewById<TextView>(R.id.txt)
                var backgroud = tabView.findViewById<LinearLayout>(R.id.background)

                textView.setTextColor(Color.parseColor("#FFFFFF"))
                backgroud.setBackgroundResource(R.drawable.tab_unselected)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabView = tab!!.customView
                var textView = tabView!!.findViewById<TextView>(R.id.txt)
                var background = tabView.findViewById<LinearLayout>(R.id.background)

                textView.setTextColor(Color.parseColor("#01B4FF"))
                background.setBackgroundResource(R.drawable.tab_selected)
            }
        })

        return binding.root
    }

    private fun getData() {
        daqiqaCategoryList = ArrayList()
        daqiqaCategoryList.add(DaqiqaCategory("Daqiqa to'plamlar", 0))
        daqiqaCategoryList.add(DaqiqaCategory("Foydali almashinuv", 1))
        daqiqaCategoryList.add(DaqiqaCategory("Construktor abonentlari", 2))
    }

    private fun setTabs(tab: TabLayout.Tab, position: Int) {
        var itemTabBinding = ItemTabSelectedBinding.inflate(layoutInflater)
        tab.customView = itemTabBinding.root
        itemTabBinding.txt.text = daqiqaCategoryList[position].name
        if (position == 0) {
            itemTabBinding.background.setBackgroundResource(R.drawable.tab_selected)
            itemTabBinding.txt.setTextColor(Color.parseColor("#01B4FF"))
        } else {
            itemTabBinding.txt.setTextColor(Color.parseColor("#FFFFFF"))
            itemTabBinding.background.setBackgroundResource(R.drawable.tab_unselected)
        }

        binding.tabLayout.getTabAt(position)?.customView = itemTabBinding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DaqiqaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DaqiqaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}