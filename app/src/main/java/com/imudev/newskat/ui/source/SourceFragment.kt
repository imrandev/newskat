package com.imudev.newskat.ui.source

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imudev.newskat.R
import com.imudev.newskat.databinding.FragmentSourceBinding
import com.imudev.newskat.ui.base.BaseFragment

class SourceFragment : BaseFragment<FragmentSourceBinding>() {

    private lateinit var fragmentSourceBinding: FragmentSourceBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_source
    }

    override fun intViewBinding(viewBinding: FragmentSourceBinding) {
        fragmentSourceBinding = viewBinding;
    }
}