package com.example.myvk.presentation.ui.view.fragments.news.adapter

import android.graphics.Bitmap
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.databinding.*
import com.example.myvk.presentation.ui.view.fragments.news.adapter.items.BaseItem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.view.*


class NewsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<BaseItem>()
    private var screenWidth: Int = 0

    fun setScreenWidth(screenWidth: Int) {
        this.screenWidth = screenWidth
    }

    fun setItems(newItems: List<BaseItem>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    class HeaderViewHolder(val binding: HeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.Header) {
            Picasso.with(itemView.context).load(newsModel.groupIcon).into(binding.newsGroupIcon)
            binding.newsGroupName.text = newsModel.groupName
            binding.newsDate.text = newsModel.date
        }
    }

    class TextContentViewHolder(val binding: TextContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.TextContent) {
            binding.newsText.text = newsModel.text
        }
    }

    inner class ImageContentViewHolder(val binding: ImageContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.ImageContent) = runBlocking {
            var image: Bitmap? = null
            val job = GlobalScope.launch {
                image = Picasso.with(itemView.context).load(newsModel.photo).get()
            }
            job.join()

            val widthImage = image?.width ?: -1
            val heightImage = image?.height ?: -1
            if (screenWidth > widthImage) {
                binding.newsImageContent.layoutParams.height = heightImage
            } else {
                binding.newsImageContent.layoutParams.height =
                    ((screenWidth.toDouble() / widthImage.toDouble()) * heightImage.toDouble()).toInt()
            }
            binding.newsImageContent.setImageBitmap(image)
        }
    }

    class StateViewHolder(val binding: StatisticsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.Statistics) {
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
            BaseItem.TYPE_HEADER -> HeaderViewHolder(binding = HeaderItemBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false))
            BaseItem.TYPE_TEXT_CONTENT -> TextContentViewHolder(binding = TextContentItemBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false))
            BaseItem.TYPE_IMAGE_CONTENT -> ImageContentViewHolder(binding = ImageContentItemBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false))
            BaseItem.TYPE_STATISTICS -> StateViewHolder(binding = StatisticsItemBinding.inflate(LayoutInflater.from(
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
        val elem = list[holder.absoluteAdapterPosition]
        when (holder) {
            is HeaderViewHolder -> {
                if (elem is BaseItem.Header)
                    holder.bind(elem)
            }
            is TextContentViewHolder -> {
                if (elem is BaseItem.TextContent)
                    holder.bind(elem)
            }
            is ImageContentViewHolder -> {
                if (elem is BaseItem.ImageContent)
                    holder.bind(elem)
            }
            is StateViewHolder -> {
                if (elem is BaseItem.Statistics)
                    holder.bind(elem)
            }
            is ErrorViewHolder -> {
                if (elem is BaseItem.Error)
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