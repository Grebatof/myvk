package com.example.myvk.presentation.ui.view.fragments.groups.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.databinding.GroupItemBinding
import com.example.myvk.domain.model.GroupModel
import com.example.myvk.presentation.ui.view.fragments.groups.adapter.items.BaseItem
import com.squareup.picasso.Picasso

class GroupsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<BaseItem>()

    fun setItems(newItems: List<BaseItem>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    class GroupsViewHolder(val binding: GroupItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(groupModel: GroupModel) {
            groupModel.groupIcon?.let { url ->
                Picasso.with(itemView.context).load(url).into(binding.groupIcon)
            }

            binding.groupName.text = "${groupModel.groupName}"
        }
    }

    class ErrorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var txtError: TextView = itemView.findViewById(R.id.error)

        fun bind(message: String) {
            txtError.text = message
        }
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BaseItem.TYPE_GROUP -> GroupsViewHolder(binding = GroupItemBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false))
            BaseItem.TYPE_ERROR -> ErrorViewHolder(itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.error_item, parent, false))
            BaseItem.TYPE_LOADING -> LoadingViewHolder(itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.loading_item, parent, false))
            else -> throw Exception("Не знаем таких")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val elem = list[position]
        when (holder) {
            is GroupsViewHolder -> {
                if (elem is BaseItem.Group)
                    holder.bind(elem.group)
            }
            is ErrorViewHolder -> {
                if (elem is BaseItem.Error)
                    elem.message.let { holder.bind(it) }
            }
            is LoadingViewHolder -> {}
            else -> throw Exception("Не знаю такого")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemCount(): Int = list.size
}