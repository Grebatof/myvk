package com.example.myvk.presentation.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myvk.R
import com.example.myvk.databinding.ActivityMainBinding
import com.example.myvk.presentation.ui.view.fragment.friends.FriendsFragment
import com.example.myvk.presentation.ui.view.fragment.GroupsFragment
import com.example.myvk.presentation.ui.view.fragment.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    //var mainViewModel: MainViewModel? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val newsFragment = NewsFragment()
        val friendsFragment = FriendsFragment()
        val groupsFragment = GroupsFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_wrapper, newsFragment)
            add(R.id.fl_wrapper, friendsFragment)
            hide(friendsFragment)
            add(R.id.fl_wrapper, groupsFragment)
            hide(groupsFragment)
            commit()
        }

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
            supportFragmentManager.fragments.forEach {
                if (it.isVisible) {
                    hide(it)
                    show(fragment)
                    commit()
                    return
                }
            }
        }
    }
}