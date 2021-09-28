package com.example.myvk.presentation.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myvk.R
import com.example.myvk.databinding.ActivityLoginBinding
import com.example.myvk.databinding.ActivityMainBinding
import com.example.myvk.presentation.ui.view.fragment.Friends.FriendsFragment
import com.example.myvk.presentation.ui.view.fragment.GroupsFragment
import com.example.myvk.presentation.ui.view.fragment.NewsFragment
import com.example.myvk.presentation.ui.view.fragment.Friends.FriendsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView





class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    var friendsViewModel: FriendsViewModel? = null

    var fragmentList: MutableList<Fragment> = ArrayList()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        friendsViewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)

        val newsFragment = NewsFragment()
        val friendsFragment = FriendsFragment()
        val groupsFragment = GroupsFragment()


        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_wrapper, newsFragment)
            add(R.id.fl_wrapper, friendsFragment)
            add(R.id.fl_wrapper, groupsFragment)
            fragmentList.add(newsFragment)
            fragmentList.add(friendsFragment)
            fragmentList.add(groupsFragment)
            commit()
        }

        displayFragment(newsFragment)

        findViewById<BottomNavigationView>(binding.bottomNavigation.id).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_news -> displayFragment(newsFragment)
                R.id.ic_friends -> displayFragment(friendsFragment)
                R.id.ic_groups -> displayFragment(groupsFragment)
            }
            true
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            for (i in fragmentList.indices)
                hide(fragmentList[i])
            show(fragment)
            commit()
        }
    }
}