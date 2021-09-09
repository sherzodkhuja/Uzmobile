package com.example.uzmobileapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.databinding.ItemRvBinding
import com.example.uzmobileapp.databinding.ItemSmsBinding
import com.example.uzmobileapp.databinding.ItemViewpagerBinding
import com.example.uzmobileapp.models.Tariflar
import com.example.uzmobileapp.models.internet.Internet
import com.example.uzmobileapp.models.sms.Sms

class PagerAdapter2(private val list: List<Tariflar>, var onClick: OnItemClickListener) :
    RecyclerView.Adapter<PagerAdapter2.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(var itemViewpagerBinding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(itemViewpagerBinding.root) {

        fun onBind(tariflar: Tariflar) {

            itemViewpagerBinding.tarifNameTv.text = tariflar.name
            itemViewpagerBinding.tarifMinut.text = tariflar.minute_inside
            itemViewpagerBinding.tarifMb.text = tariflar.mb
            itemViewpagerBinding.tarifSms.text = tariflar.sms
            itemViewpagerBinding.montlyCost.text = tariflar.monthly_cost

            itemViewpagerBinding.tarifCard.setOnClickListener {
                onClick.onItemClick(tariflar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemClick(tariflar: Tariflar)
    }
}
