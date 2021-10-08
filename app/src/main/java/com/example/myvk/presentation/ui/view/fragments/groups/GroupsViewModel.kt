package com.example.myvk.presentation.ui.view.fragments.groups

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.GroupModel
import com.example.myvk.domain.use_case.VKGroupsRequest
import com.example.myvk.presentation.ui.view.fragments.groups.adapter.items.BaseItem
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class GroupsViewModel(application: Application) : AndroidViewModel(application) {
    var listGroupsLiveData = MutableLiveData<List<BaseItem>>()

    fun loadGroups() {
        listGroupsLiveData.postValue(listOf(BaseItem.Loading))
        VK.execute(VKGroupsRequest(), object : VKApiCallback<List<GroupModel>> {
            override fun success(result: List<GroupModel>) {
                listGroupsLiveData.postValue(result.map { BaseItem.Group(it) })
            }

            override fun fail(error: Exception) {
                Log.d("!!!", "Error is ${error.toString()}")
                listGroupsLiveData.postValue(listOf(BaseItem.Error(error.toString())))
            }
        })
    }
}