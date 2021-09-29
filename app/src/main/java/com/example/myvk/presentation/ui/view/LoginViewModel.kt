package com.example.myvk.presentation.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

class LoginViewModel(application: Application): AndroidViewModel(application) {

    val loginSuccessLiveData = MutableLiveData<Boolean>()

    fun loginVK(): VKAuthCallback{
        return object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                loginSuccessLiveData.value = true
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
    }
}