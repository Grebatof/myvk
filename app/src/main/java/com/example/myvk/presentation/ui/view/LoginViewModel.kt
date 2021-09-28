package com.example.myvk.presentation.ui.view

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

class LoginViewModel(application: Application): AndroidViewModel(application) {

    val loginSuccessLiveData = MutableLiveData<Boolean>()

    fun loginVK(): VKAuthCallback{
        return object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                VK.execute(VKFriendsRequest(), object: VKApiCallback<List<FriendModel>> {
                    override fun success(result: List<FriendModel>) {
                        for (i in result) {
                            Log.d("!!!", "${i.firstName} ${i.lastName} ${i.avatar}")
                        }
                        loginSuccessLiveData.value = true

                    }
                    override fun fail(error: Exception) {
                        Toast.makeText(getApplication(), "Error is ${error.toString()}", Toast.LENGTH_LONG).show()
                        Log.d("!!!", "Error is ${error.toString()}")
                    }
                })
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
    }
}