package com.imudev.newskat.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.imudev.newskat.R
import com.imudev.newskat.databinding.ActivityMainBinding
import com.imudev.newskat.ui.base.BaseActivity
import com.imudev.newskat.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var mainViewModel: MainViewModel
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(activityMainBinding.appbar.toolbar)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setUpBottomNavigation()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun intViewBinding(viewBinding: ActivityMainBinding) {
        this.activityMainBinding = viewBinding
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    private fun setUpBottomNavigation() {
        activityMainBinding.bottomNavigation.setupWithNavController(navHostFragment.findNavController())
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.headlineFragment,
                R.id.sourceFragment,
                R.id.searchFragment
            )
        )
        setupActionBarWithNavController(navHostFragment.findNavController(), appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_setting -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}