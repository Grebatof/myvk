package com.example.myvk.presentation.ui.view.fragment.friends.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.BaseItem
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.Error
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.Friend
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.Loading
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
            txtCity.text = itemView.context.getString(R.string.friend_no_city)
            friendModel.city?.let { city -> txtCity.text = city }

            if (friendModel.isOnline) {
                imgOnline.visibility = View.VISIBLE
            } else {
                imgOnline.visibility = View.GONE
            }
        }
    }

    class ErrorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun makeToast(message: String) {
            Toast.makeText(itemView.context, "Error is $message", Toast.LENGTH_LONG).show()
        }
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var txtUsername: TextView = itemView.findViewById(R.id.friend_txt_username)
        private var txtCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        fun bind() {
            txtUsername.text = "Загрузка"
            txtCity.text = "Загрузка"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return when (viewType) {
            BaseItem.TYPE_FRIEND -> FriendsViewHolder(itemView = itemView)
            BaseItem.TYPE_ERROR -> ErrorViewHolder(itemView = itemView)
            BaseItem.TYPE_LOADING -> LoadingViewHolder(itemView = itemView)
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
                    elem.message.let { holder.makeToast(it) }
            }
            is LoadingViewHolder -> {
                if (elem is Loading) {
                    holder.bind()
                }
            }
            else -> throw Exception("Не знаю такого")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemCount(): Int = list.size
}