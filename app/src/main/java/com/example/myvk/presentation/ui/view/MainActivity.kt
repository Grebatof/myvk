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
import com.example.myvk.presentation.ui.view.fragment.Friends.FriendsFragment
import com.example.myvk.presentation.ui.view.fragment.GroupsFragment
import com.example.myvk.presentation.ui.view.fragment.NewsFragment
import com.example.myvk.presentation.ui.view.fragment.Friends.FriendsViewModel
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

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }


}