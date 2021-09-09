package com.example.uzmobileapp.adapters.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobileapp.R
import com.example.uzmobileapp.databinding.ExpansionPanelRecyclerCellBinding
import com.example.uzmobileapp.databinding.ItemNewsBinding
import com.example.uzmobileapp.databinding.ItemUssdBinding
import com.example.uzmobileapp.models.Ussd
import com.example.uzmobileapp.models.news.News
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.squareup.picasso.Picasso

class NewsAdapter(var list: List<News>, var onClick: OnItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.Vh>() {

    inner class Vh(var itemNewsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemNewsBinding.root) {

        fun onBind(news: News) {
            itemNewsBinding.newsTitle.text = news.title
            Picasso.get().load(news.image).into(itemNewsBinding.newsPicture)
            itemNewsBinding.cardNews.setOnClickListener {
                onClick.onItemClick(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemNewsBinding.inflate(
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
        fun onItemClick(news: News)
    }
}