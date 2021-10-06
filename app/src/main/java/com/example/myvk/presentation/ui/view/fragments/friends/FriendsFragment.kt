package com.example.myvk.presentation.ui.view.fragments.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvk.databinding.FragmentFriendsBinding
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.FriendsAdapter

class FriendsFragment : Fragment() {

    val friendsViewModel: FriendsViewModel by viewModels()

    private lateinit var binding: FragmentFriendsBinding
    private val friendsAdapter: FriendsAdapter = FriendsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerFriends.layoutManager = LinearLayoutManager(context)
        binding.recyclerFriends.adapter = friendsAdapter
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            friendsViewModel.loadFriends()
            friendsViewModel.listFriendsLiveData.observe(this, Observer {
                friendsAdapter.setItems(it)
            })
        }
        super.onHiddenChanged(hidden)
    }
}