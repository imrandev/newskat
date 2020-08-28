package com.imudev.newskat.ui.search

import android.os.Bundle
import android.view.View
import com.imudev.newskat.R
import com.imudev.newskat.databinding.FragmentSearchBinding
import com.imudev.newskat.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var fragmentSearchBinding: FragmentSearchBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_search;
    }

    override fun intViewBinding(viewBinding: FragmentSearchBinding) {
        fragmentSearchBinding = viewBinding;
    }
}