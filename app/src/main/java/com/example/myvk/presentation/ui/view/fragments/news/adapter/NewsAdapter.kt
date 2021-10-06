package com.example.myvk.presentation.ui.view.fragments.news.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.databinding.FragmentNewsBinding
import com.example.myvk.databinding.NewsItemBinding
import com.example.myvk.domain.model.NewsModel
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.BaseItem
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.Error
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.News
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<BaseItem>()

    fun setItems(newItems: List<BaseItem>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: NewsModel) {
            val simpleDateFormat = java.text.SimpleDateFormat("dd MMMM HH:mm")
            val date = java.util.Date(newsModel.date.toLong() * 1000)

            Picasso.with(itemView.context).load(newsModel.groupIcon).into(binding.newsGroupIcon)
            binding.newsGroupName.text = newsModel.groupName
            binding.newsDate.text = simpleDateFormat.format(date)
            binding.newsText.text = newsModel.text
            binding.newsLikes.text = "${newsModel.likes} лайков"
            binding.newsComments.text = "${newsModel.comments} комментариев"
            binding.newsReposts.text = "${newsModel.reposts} репостов"
            binding.newsViews.text = "${newsModel.views} просмотров"
        }
    }

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txtError: TextView = itemView.findViewById(R.id.error)

        fun bind(message: String) {
            txtError.text = message
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BaseItem.TYPE_NEWS -> NewsViewHolder(binding = NewsItemBinding.inflate(LayoutInflater.from(
                parent.context), parent, false))
            BaseItem.TYPE_ERROR -> ErrorViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.error_item, parent, false))
            BaseItem.TYPE_LOADING -> LoadingViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.loading_item, parent, false))
            else -> throw Exception("Не знаем таких")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val elem = list[position]
        when (holder) {
            is NewsViewHolder -> {
                if (elem is News)
                    holder.bind(elem.news)
            }
            is ErrorViewHolder -> {
                if (elem is Error)
                    elem.message.let { holder.bind(it) }
            }
            is LoadingViewHolder -> {
            }
            else -> throw Exception("Не знаю такого")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemCount(): Int = list.size
}