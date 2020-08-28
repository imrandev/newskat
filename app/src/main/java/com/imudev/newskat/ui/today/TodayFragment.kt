package com.imudev.newskat.ui.today

import android.os.Bundle
import android.view.View
import com.imudev.newskat.R
import com.imudev.newskat.databinding.FragmentTodayBinding
import com.imudev.newskat.ui.base.BaseFragment

class TodayFragment : BaseFragment<FragmentTodayBinding>() {

    lateinit var fragmentTodayBinding: FragmentTodayBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_today
    }

    override fun intViewBinding(viewBinding: FragmentTodayBinding) {
        fragmentTodayBinding = viewBinding;
    }
}