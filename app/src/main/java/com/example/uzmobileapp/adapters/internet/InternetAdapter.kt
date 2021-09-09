package com.example.uzmobileapp.adapters.internet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.databinding.ItemRvBinding
import com.example.uzmobileapp.models.internet.Internet

class InternetAdapter(private val smsList: List<Internet>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<InternetAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(var itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

        fun onBind(internet: Internet) {
            itemRvBinding.price.text = "Abonent to'lovi oyiga: ${internet.price} so'm"
            itemRvBinding.mb.text = "Berilgan trafik: ${internet.amount} MB"
            itemRvBinding.expireDate.text = "Amal qilish muddati: ${internet.expire_date} kun"
            itemRvBinding.tarifNumber.text = internet.amount
            itemRvBinding.tarifName.text = internet.name

            itemRvBinding.itemClick.setOnClickListener {
                listener.onItemClick(internet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.onBind(smsList[position])
    }

    override fun getItemCount(): Int = smsList.size

    interface OnItemClickListener {
        fun onItemClick(internet: Internet)
    }
}
