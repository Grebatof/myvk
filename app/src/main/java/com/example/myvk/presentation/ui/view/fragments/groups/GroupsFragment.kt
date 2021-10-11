package com.example.myvk.presentation.ui.view.fragments.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvk.databinding.FragmentGroupsBinding
import com.example.myvk.presentation.ui.view.fragments.groups.adapter.GroupsAdapter

class GroupsFragment : Fragment() {
    private val groupsViewModel: GroupsViewModel by viewModels()

    private lateinit var binding: FragmentGroupsBinding
    private val groupsAdapter: GroupsAdapter = GroupsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentGroupsBinding.inflate(LayoutInflater.from(container?.context), container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerGroups.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupsAdapter
            groupsViewModel.loadGroups()
            groupsViewModel.listGroupsLiveData.observe(viewLifecycleOwner, Observer {
                groupsAdapter.setItems(it)
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }
}