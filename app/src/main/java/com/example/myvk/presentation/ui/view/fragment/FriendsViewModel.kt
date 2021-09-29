package com.example.myvk.presentation.ui.view.fragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class FriendsViewModel(application: Application): AndroidViewModel(application) {

    var listFriendsLiveData = MutableLiveData<List<FriendModel>>()

    fun loadFriends(){
        VK.execute(VKFriendsRequest(), object: VKApiCallback<List<FriendModel>> {
            override fun success(result: List<FriendModel>) {
                for (i in result) {
                    Log.d("friends", "${i.firstName} ${i.lastName} ${i.avatar} ${i.city} ${i.isOnline}")
                }
                listFriendsLiveData.value = result
            }
            override fun fail(error: Exception) {
                Toast.makeText(getApplication(), "Error is ${error.toString()}", Toast.LENGTH_LONG).show()
                Log.d("!!!", "Error is ${error.toString()}")
            }
        })
    }
}