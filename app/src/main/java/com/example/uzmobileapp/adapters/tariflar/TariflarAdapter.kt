package com.example.uzmobileapp.adapters.tariflar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.databinding.ItemTariflarBinding
import com.example.uzmobileapp.models.Tariflar

class TariflarAdapter(var list: List<Tariflar>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<TariflarAdapter.Vh>() {

    inner class Vh(var itemTariflarBinding: ItemTariflarBinding) :
        RecyclerView.ViewHolder(itemTariflarBinding.root) {

        fun onBind(tariflar: Tariflar) {
            itemTariflarBinding.tarifName.text = tariflar.name
            itemTariflarBinding.price.text = "Abonent to'lovi oyiga:" + tariflar.monthly_cost
            itemTariflarBinding.minute.text = "O'zbekiston bo'yicha:" + tariflar.minute_uzb
            itemTariflarBinding.mb.text = "Mobil internet:" + tariflar.mb
            itemTariflarBinding.sms.text = "Ozbekiston bo'yicha:" + tariflar.sms

            itemTariflarBinding.tarifCard.setOnClickListener {
                onItemClickListener.onItemClick(tariflar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemTariflarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemClick(tariflar: Tariflar)
    }
}