package com.example.myvk.presentation.ui.view

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKScope

class MainViewModel(application: Application): AndroidViewModel(application) {

    val inputLiveData = MutableLiveData<List<FriendModel>>()
    val outputLiveData = MutableLiveData<List<FriendModel>>()

    init {
        privet()
    }

    fun privet() {
    }
}