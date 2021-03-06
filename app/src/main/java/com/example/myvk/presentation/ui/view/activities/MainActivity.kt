package com.example.myvk.presentation.ui.view.activities

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myvk.R
import com.example.myvk.databinding.ActivityMainBinding
import com.example.myvk.presentation.ui.view.fragments.friends.FriendsFragment
import com.example.myvk.presentation.ui.view.fragments.groups.GroupsFragment
import com.example.myvk.presentation.ui.view.fragments.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    //var mainViewModel: MainViewModel? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar
        setContentView(binding.root)

        //mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val newsFragment = NewsFragment()
        val friendsFragment = FriendsFragment()
        val groupsFragment = GroupsFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_wrapper, newsFragment)
            hide(newsFragment)
            add(R.id.fl_wrapper, friendsFragment)
            hide(friendsFragment)
            add(R.id.fl_wrapper, groupsFragment)
            hide(groupsFragment)
            commit()
        }

        supportFragmentManager.beginTransaction().show(newsFragment).commit()

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
        var position: Int = 1
        supportFragmentManager.fragments.forEach {
            if (it.isVisible) {
                // ???????? ???????????????? ?????????????? ?????? ???????????????? ????????????????
                if (it == fragment) {
                    return
                }
                // ???????? ???? ???? ?????????????????? fragment ?? ?????????????? supportFragmentManager.fragments, ???????????? ???????????? ?????? ???????????????? ????????????
                if (position == 1)
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .show(fragment)
                        .hide(it)
                        .commit()
                // ???????? ???? ?????????????????? fragment ?? ?????????????? supportFragmentManager.fragments, ???????????? ???????????? ?????? ???????????????? ??????????
                else if (position == -1)
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                        .show(fragment)
                        .hide(it)
                        .commit()
                return
            }
            if (it == fragment && position == 1) {
                position = -1
            }
        }
    }
}