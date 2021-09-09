package com.example.uzmobileapp.adapters.ussd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.R
import com.example.uzmobileapp.databinding.ExpansionPanelRecyclerCellBinding
import com.example.uzmobileapp.databinding.ItemUssdBinding
import com.example.uzmobileapp.models.Ussd
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection

class UssdAdapter(var list: List<Ussd>, var onClick: OnItemClickListener) :
    RecyclerView.Adapter<UssdAdapter.Vh>() {

    private val expansion = ExpansionLayoutCollection()

    inner class Vh(var itemUssdBinding: ExpansionPanelRecyclerCellBinding) :
        RecyclerView.ViewHolder(itemUssdBinding.root) {

        fun onBind(ussd: Ussd) {
            itemUssdBinding.code.text = ussd.code
            itemUssdBinding.name.text = ussd.name
            itemUssdBinding.btn.setOnClickListener {

                onClick.onItemClick(ussd)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ExpansionPanelRecyclerCellBinding.inflate(
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
        fun onItemClick(ussd: Ussd)
    }
}