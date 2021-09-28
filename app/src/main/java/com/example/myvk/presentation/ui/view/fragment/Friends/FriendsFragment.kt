package com.example.myvk.presentation.ui.view.fragment.Friends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.R

class FriendsFragment : Fragment() {

    var friendsViewModel: FriendsViewModel? = null
    var mRvFriends: RecyclerView? = null
    var mAdapter: FriendsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        Log.d("!!!", "3")


        friendsViewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)

        friendsViewModel!!.inputLiveData.observe(viewLifecycleOwner, Observer {
            mRvFriends = inflater.inflate(R.layout.fragment_groups, container, false).findViewById(R.id.recycler_friends)

            Log.d("!!!", "5")

            mAdapter = FriendsAdapter()

            mRvFriends!!.adapter = FriendsAdapter()
            mRvFriends!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            mRvFriends!!.setHasFixedSize(true)

        })

        Log.d("!!!", "4")

        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}