package com.example.myvk.presentation.ui.view.fragments.news.adapter

import android.content.Context.WINDOW_SERVICE
import android.graphics.Bitmap
import android.graphics.Point
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
import android.view.WindowManager


class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<BaseItem>()

    fun setItems(newItems: List<BaseItem>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    class HeaderViewHolder(val binding: HeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.Header) {
            val simpleDateFormat = java.text.SimpleDateFormat("dd MMMM HH:mm")
            val date = java.util.Date(newsModel.date.toLong() * 1000)

            Picasso.with(itemView.context).load(newsModel.groupIcon).into(binding.newsGroupIcon)
            binding.newsGroupName.text = newsModel.groupName
            binding.newsDate.text = simpleDateFormat.format(date)
        }
    }

    class TextContentViewHolder(val binding: TextContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.TextContent) {
            binding.newsText.text = newsModel.text
        }
    }

    class ImageContentViewHolder(val binding: ImageContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.ImageContent) = runBlocking {
            var image: Bitmap? = null
            val job = GlobalScope.launch {
                image = Picasso.with(itemView.context).load(newsModel.photo).get()
            }
            job.join()

            val windowManager = itemView.context.getSystemService(WINDOW_SERVICE) as WindowManager

            val display: Display = windowManager.defaultDisplay
            val point = Point()
            display.getSize(point)
            val screenWidth: Int = point.x

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

    class StateViewHolder(val binding: StateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: BaseItem.State) {
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
            BaseItem.TYPE_STATE -> StateViewHolder(binding = StateItemBinding.inflate(LayoutInflater.from(
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
                if (elem is BaseItem.State)
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