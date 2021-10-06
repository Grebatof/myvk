package com.example.myvk.presentation.ui.view.fragments.friends.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.BaseItem
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.Error
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.Friend
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<BaseItem>()

    fun setItems(newItems: List<BaseItem>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var avatar: CircleImageView = itemView.findViewById(R.id.friend_civ_avatar)
        private var txtUsername: TextView = itemView.findViewById(R.id.friend_txt_username)
        private var txtCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        private var imgOnline: View = itemView.findViewById(R.id.friend_img_online)

        fun bind(friendModel: FriendModel) {
            friendModel.avatar?.let { url ->
                Picasso.with(itemView.context).load(url).into(avatar)
            }

            txtUsername.text = "${friendModel.firstName} ${friendModel.lastName}"
            friendModel.city?.let { city -> txtCity.text = city } ?: kotlin.run {
                txtCity.text = itemView.context.getString(R.string.friend_no_city)
            }

            if (friendModel.isOnline) {
                imgOnline.visibility = View.VISIBLE
            } else {
                imgOnline.visibility = View.GONE
            }
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
            BaseItem.TYPE_FRIEND -> FriendsViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false))
            BaseItem.TYPE_ERROR -> ErrorViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.error_item, parent, false))
            BaseItem.TYPE_LOADING -> LoadingViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false))
            else -> throw Exception("Не знаем таких")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val elem = list[position]
        when (holder) {
            is FriendsViewHolder -> {
                if (elem is Friend)
                    holder.bind(elem.friend)
            }
            is ErrorViewHolder -> {
                if (elem is Error)
                    elem.message.let { holder.bind(it) }
            }
            is LoadingViewHolder -> {}
            else -> throw Exception("Не знаю такого")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemCount(): Int = list.size
}