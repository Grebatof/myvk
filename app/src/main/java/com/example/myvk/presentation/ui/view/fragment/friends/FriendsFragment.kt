package com.example.myvk.presentation.ui.view.fragment.friends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvk.databinding.FragmentFriendsBinding
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.FriendsAdapter

class FriendsFragment : Fragment() {

    val friendsViewModel: FriendsViewModel by viewModels()

    private lateinit var binding: FragmentFriendsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Log.d("onViewCreated", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            binding.recyclerFriends.layoutManager = LinearLayoutManager(context)
            friendsViewModel.loadFriends()
            friendsViewModel.listFriendsLiveData.observe(this, Observer {
                binding.recyclerFriends.adapter = friendsViewModel.listFriendsLiveData.value?.let {
                    FriendsAdapter(it)
                }
            })
        }
        super.onHiddenChanged(hidden)
    }
}