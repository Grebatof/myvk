package com.example.myvk.presentation.ui.view.fragment.friends.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.BaseItem
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter(private val friends: List<FriendModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<BaseItem>()

    fun setItems(newItems: List<BaseItem>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
        //notifyItemRemoved()
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

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        Log.d("!!!!!!", "${viewType.toString()}")
        return FriendsViewHolder(itemView = itemView)
        when (viewType) {
            BaseItem.TYPE_FRIEND -> return FriendsViewHolder(itemView = itemView)
            BaseItem.TYPE_ERROR -> return ErrorViewHolder(itemView = itemView)
            BaseItem.TYPE_LOADING -> return LoadingViewHolder(itemView = itemView)
            else -> throw Exception("Не знаем таких")
        }

        //return FriendsViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FriendsViewHolder -> {
                holder.bind(friendModel = friends[position])
            }
        }
    }

    //override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemCount(): Int = friends.size
}