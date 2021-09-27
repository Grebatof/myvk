package com.example.myvk.presentation.ui.view.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R
import com.example.myvk.domain.model.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import android.util.Log

class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var FriendsList: List<FriendModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("!!!", "8")
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.friend_item, parent, false)
        return FriendsViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("!!!", "7")
        if (holder is FriendsViewHolder) {
            FriendsList?.get(position)?.let { holder.bind(friendModel = it) }
        }
    }

    override fun getItemCount(): Int {
        return FriendsList!!.count()
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.friend_civ_avatar)
        private var mTxtUsername: TextView = itemView.findViewById(R.id.friend_txt_username)
        private var mTxtCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        private var mImgOnline: View = itemView.findViewById(R.id.friend_img_online)

        @SuppressLint("SetTextI18n")
        fun bind(friendModel: FriendModel) {
            Log.d("!!!", "6")
            friendModel.avatar?.let { url ->
                Picasso.with(itemView.context).load(url)
                    .into(mCivAvatar)
            }

            mTxtUsername.text = "${friendModel.firstName} ${friendModel.lastName}"
            mTxtCity.text = itemView.context.getString(R.string.friend_no_city)
            friendModel.city?.let { city -> mTxtCity.text = city }

            if (friendModel.isOnline) {
                mImgOnline.visibility = View.VISIBLE
            } else {
                mImgOnline.visibility = View.GONE
            }
        }
    }
}