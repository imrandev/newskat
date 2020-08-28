package com.imudev.newskat.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.imudev.newskat.R
import com.imudev.newskat.databinding.ActivityMainBinding
import com.imudev.newskat.viewmodel.MainViewModel
import com.imudev.newskat.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.init(this)
        mainViewModel.getHeadlines().observe(this, Observer {
            // this is the trigger point when headline data has changed
            Log.d(TAG, "onCreate: " + it.size)
        })
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun intViewBinding(viewBinding: ActivityMainBinding) {
        this.activityMainBinding = viewBinding;
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    fun setUpBottomNavigation() {

        // Finding the Navigation Controller
        val navController = findNavController(R.id.fragmentNavHost)

        // Setting Navigation Controller with the BottomNavigationView
        activityMainBinding.bottomNavigation.setupWithNavController(navController)

        // Setting Up ActionBar with Navigation Controller
        // Pass the IDs of top-level destinations in AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.todayFragment,
                R.id.sourceFragment,
                R.id.searchFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}