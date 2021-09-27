package com.example.myvk.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myvk.domain.model.FriendModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainViewModel(application: Application): AndroidViewModel(application) {

    val inputLiveData = MutableLiveData<List<FriendModel>>()
    val outputLiveData = MutableLiveData<List<FriendModel>>()

    init {
        privet()
    }

    fun privet() {
        inputLiveData.observe(getApplication(), Observer {
            outputLiveData.value = it
        })
    }
}