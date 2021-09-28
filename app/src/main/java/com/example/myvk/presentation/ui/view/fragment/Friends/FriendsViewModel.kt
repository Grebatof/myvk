package com.example.myvk.presentation.ui.view.fragment.Friends

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.presentation.ui.view.MainActivity

class FriendsViewModel(application: Application): AndroidViewModel(application) {

    val inputLiveData = MutableLiveData<List<FriendModel>>()
    val outputLiveData = MutableLiveData<List<FriendModel>>()

    init {
        //frBase()
    }

    fun data(items: List<FriendModel>) {
        inputLiveData.value = items
    }
}