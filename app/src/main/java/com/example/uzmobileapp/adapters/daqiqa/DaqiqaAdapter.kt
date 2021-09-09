package com.example.uzmobileapp.adapters.daqiqa

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.databinding.ItemDaqiqaBinding
import com.example.uzmobileapp.databinding.ItemRvBinding
import com.example.uzmobileapp.models.daqiqa.Daqiqa
import com.example.uzmobileapp.models.internet.Internet

class DaqiqaAdapter(private val daqiqaList: List<Daqiqa>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<DaqiqaAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(var itemDaqiqaBinding: ItemDaqiqaBinding) :
        RecyclerView.ViewHolder(itemDaqiqaBinding.root) {

        fun onBind(daqiqa: Daqiqa) {
            itemDaqiqaBinding.daqiqaName.text = daqiqa.name
            itemDaqiqaBinding.daqiqaAmount.text = "Berilgan daqiqalar soni: ${daqiqa.minut}"
            itemDaqiqaBinding.daqiqaNumber.text = daqiqa.minut
            itemDaqiqaBinding.expireDate.text = "Amal qilish muddati: ${daqiqa.expire_date}"
            itemDaqiqaBinding.price.text = "Narxi: ${daqiqa.price}"

            itemDaqiqaBinding.tarifCard.setOnClickListener {
                listener.onItemClick(daqiqa)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemDaqiqaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.onBind(daqiqaList[position])
    }

    override fun getItemCount(): Int = daqiqaList.size

    interface OnItemClickListener {
        fun onItemClick(daqiqa: Daqiqa)
    }
}
