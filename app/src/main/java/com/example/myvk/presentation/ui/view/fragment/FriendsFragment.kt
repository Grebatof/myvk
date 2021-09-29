package com.example.myvk.presentation.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvk.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {

    var friendsViewModel: FriendsViewModel? = null

    private lateinit var binding: FragmentFriendsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        friendsViewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)

        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            binding.recyclerFriends.layoutManager = LinearLayoutManager(context)
            friendsViewModel?.loadFriends()
            friendsViewModel?.listFriendsLiveData?.observe(this, Observer {
                binding.recyclerFriends.adapter = friendsViewModel?.listFriendsLiveData?.value?.let {
                    FriendsAdapter(it)
                }
            })
        }
        super.onHiddenChanged(hidden)
    }
}