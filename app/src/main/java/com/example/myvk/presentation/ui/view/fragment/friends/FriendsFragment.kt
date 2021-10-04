package com.example.myvk.presentation.ui.view.fragment.friends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvk.databinding.FragmentFriendsBinding
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.FriendsAdapter

class FriendsFragment : Fragment() {

    val friendsViewModel: FriendsViewModel by viewModels()

    private lateinit var binding: FragmentFriendsBinding
    private val privet: FriendsAdapter = FriendsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerFriends.layoutManager = LinearLayoutManager(context)
        binding.recyclerFriends.adapter = privet
        Log.d("onViewCreated", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        Log.d("onResume", "onResume")
        super.onResume()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            friendsViewModel.loadFriends()
            friendsViewModel.listFriendsLiveData.observe(this, Observer {
                friendsViewModel.listFriendsLiveData.value?.let {
                    privet.setItems(it)
                }
            })
        }
        super.onHiddenChanged(hidden)
    }
}