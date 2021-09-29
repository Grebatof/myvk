package com.example.myvk.presentation.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.myvk.R
import com.example.myvk.databinding.ActivityLoginBinding
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class LoginActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    var loginViewModel: LoginViewModel? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            binding.buttonLogin.visibility = View.INVISIBLE
            VK.login(this, arrayListOf(VKScope.FRIENDS, VKScope.GROUPS))
        }

        loginViewModel?.loginSuccessLiveData?.observe(this) {
            if (it == true)
                startActivity(Intent(applicationContext, MainActivity::class.java))
            binding.buttonLogin.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = loginViewModel?.loginVK()
        callback ?: return
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}