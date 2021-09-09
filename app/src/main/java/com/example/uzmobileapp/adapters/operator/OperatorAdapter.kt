package com.example.uzmobileapp.adapters.operator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.R
import com.example.uzmobileapp.databinding.ExpansionPanelRecyclerCellBinding
import com.example.uzmobileapp.databinding.ItemOperatorBinding
import com.example.uzmobileapp.databinding.ItemUssdBinding
import com.example.uzmobileapp.models.Operator
import com.example.uzmobileapp.models.Ussd
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection

class OperatorAdapter(var list: List<Operator>, var onClick: OnItemClickListener) :
    RecyclerView.Adapter<OperatorAdapter.Vh>() {

    private val expansion = ExpansionLayoutCollection()

    inner class Vh(var itemOperatorBinding: ItemOperatorBinding) :
        RecyclerView.ViewHolder(itemOperatorBinding.root) {

        fun onBind(operator: Operator) {
            itemOperatorBinding.phoneOperator.text = operator.phone
            itemOperatorBinding.titleOperator.text = operator.description

            itemOperatorBinding.cardOperator.setOnClickListener {
                onClick.onItemClick(operator)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemOperatorBinding.inflate(
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
        fun onItemClick(operator: Operator)
    }
}