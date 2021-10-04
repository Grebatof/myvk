package com.example.myvk.presentation.ui.view.fragment.friends

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.BaseItem
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.Error
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.Friend
import com.example.myvk.presentation.ui.view.fragment.friends.adapter.items.Loading
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class FriendsViewModel(application: Application): AndroidViewModel(application) {

    var listFriendsLiveData = MutableLiveData<List<BaseItem>>()
    var list = ArrayList<BaseItem>()

    fun loadFriends(){
        list.clear()
        list.add(Loading)
        listFriendsLiveData.postValue(list)
        VK.execute(VKFriendsRequest(), object: VKApiCallback<List<FriendModel>> {
            override fun success(result: List<FriendModel>) {
                list.clear()
                for (i in result) {
                    Log.d("friends", "${i.firstName} ${i.lastName} ${i.avatar.toString()} ${i.isOnline}")
                    list.add(Friend(i))
                }
                listFriendsLiveData.postValue(list)
            }
            override fun fail(error: Exception) {
                list.clear()
                list.add(Error(error.toString()))
                Log.d("!!!", "Error is ${error.toString()}")
                listFriendsLiveData.postValue(list)
            }
        })
    }
}