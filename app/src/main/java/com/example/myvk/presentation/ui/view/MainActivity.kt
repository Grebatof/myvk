package com.example.myvk.presentation.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myvk.R
import com.example.myvk.domain.model.FriendModel
import com.example.myvk.domain.use_case.VKFriendsRequest
import com.example.myvk.presentation.ui.view.fragment.FriendsFragment
import com.example.myvk.presentation.ui.view.fragment.GroupsFragment
import com.example.myvk.presentation.ui.view.fragment.NewsFragment
import com.example.myvk.presentation.viewmodel.FriendsViewModel
import com.example.myvk.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vk.api.sdk.*
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    var friendsViewModel: FriendsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        friendsViewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)

        VK.login(this, arrayListOf(VKScope.FRIENDS, VKScope.GROUPS))

        val newsFragment = NewsFragment()
        val friendsFragment = FriendsFragment()
        val groupsFragment = GroupsFragment()

        makeCurrentFragment(newsFragment)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_news -> makeCurrentFragment(newsFragment)
                R.id.ic_friends -> makeCurrentFragment(friendsFragment)
                R.id.ic_groups -> makeCurrentFragment(groupsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                VK.execute(VKFriendsRequest(), object: VKApiCallback<List<FriendModel>> {
                    override fun success(result: List<FriendModel>) {
                        /*for (i in result) {
                            Log.d("!!!", "${i.firstName} ${i.lastName} ${i.avatar}")
                        }*/
                        Log.d("!!!", "1")
                        friendsViewModel?.data(result)
                        Log.d("!!!", "2")

                    }
                    override fun fail(error: Exception) {
                        Toast.makeText(applicationContext, "Error is ${error.toString()}", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Error is ${error.toString()}")
                    }
                })
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}