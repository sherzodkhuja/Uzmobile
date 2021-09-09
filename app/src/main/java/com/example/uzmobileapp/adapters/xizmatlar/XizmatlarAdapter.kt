package com.example.uzmobileapp.adapters.xizmatlar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.adapters.ussd.UssdAdapter
import com.example.uzmobileapp.databinding.ItemViewpagerBinding
import com.example.uzmobileapp.databinding.ItemXizmatlarRvBinding
import com.example.uzmobileapp.models.Tariflar
import com.example.uzmobileapp.models.Ussd
import com.example.uzmobileapp.models.Xizmatlar

class XizmatlarAdapter(
    var list: List<Xizmatlar>,
    var onClick: OnItemClickListener
) : RecyclerView.Adapter<XizmatlarAdapter.Vh>() {

    inner class Vh(var itemXizmatlarRvBinding: ItemXizmatlarRvBinding) :
        RecyclerView.ViewHolder(itemXizmatlarRvBinding.root) {

        fun onBind(xizmatlar: Xizmatlar) {
            itemXizmatlarRvBinding.name.text = xizmatlar.name
            itemXizmatlarRvBinding.callBtn.setOnClickListener {
                onClick.onItemClickCall(xizmatlar)
            }

            itemXizmatlarRvBinding.batafsilBtn.setOnClickListener {
                onClick.onItemClickBatafsil(xizmatlar)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemXizmatlarRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemClickCall(xizmatlar: Xizmatlar)
        fun onItemClickBatafsil(xizmatlar: Xizmatlar)
    }
}