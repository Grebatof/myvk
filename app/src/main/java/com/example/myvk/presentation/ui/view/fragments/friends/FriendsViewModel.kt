package com.example.myvk.presentation.ui.view.fragments.friends

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.BaseItem
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.Error
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.Friend
import com.example.myvk.presentation.ui.view.fragments.friends.adapter.items.Loading
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class FriendsViewModel(application: Application) : AndroidViewModel(application) {

    var listFriendsLiveData = MutableLiveData<List<BaseItem>>()

    fun loadFriends() {
        listFriendsLiveData.postValue(listOf(Loading))
        VK.execute(VKFriendsRequest(), object : VKApiCallback<List<FriendModel>> {
            override fun success(result: List<FriendModel>) {
                listFriendsLiveData.postValue(result.map { Friend(it) })
            }

            override fun fail(error: Exception) {
                Log.d("!!!", "Error is ${error.toString()}")
                listFriendsLiveData.postValue(listOf(Error(error.toString())))
            }
        })
    }
}