package com.example.uzmobileapp.adapters.sms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.databinding.ItemRvBinding
import com.example.uzmobileapp.databinding.ItemSmsBinding
import com.example.uzmobileapp.models.internet.Internet
import com.example.uzmobileapp.models.sms.Sms

class SmsAdapter(private val smsList: List<Sms>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SmsAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(var itemSmsBinding: ItemSmsBinding) :
        RecyclerView.ViewHolder(itemSmsBinding.root) {

        fun onBind(sms: Sms) {
            itemSmsBinding.price.text = "Narxi: ${sms.price} so'm"
            itemSmsBinding.smsAmount.text = "Berilgan smslar soni: ${sms.amount} MB"
            itemSmsBinding.expireDate.text = "Amal qilish muddati: ${sms.expire_date}"
            itemSmsBinding.smsNumber.text = sms.amount
            itemSmsBinding.smsName.text = sms.name

            itemSmsBinding.itemClick.setOnClickListener {
                listener.onItemClick(sms)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemSmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.onBind(smsList[position])
    }

    override fun getItemCount(): Int = smsList.size

    interface OnItemClickListener {
        fun onItemClick(sms: Sms)
    }
}
