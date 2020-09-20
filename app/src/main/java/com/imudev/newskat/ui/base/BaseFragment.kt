package com.imudev.newskat.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.imudev.newskat.ui.main.MainActivity
import com.imudev.newskat.viewmodel.MainViewModel


abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var fragmentContext : Context
    protected lateinit var mainViewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fragmentContext = context
    }

    @LayoutRes
    protected abstract fun getLayoutRes() : Int
    protected abstract fun intViewBinding(viewBinding: V)
    protected open fun isFullWindow(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = (activity as MainActivity).mainViewModel
        val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        if (isFullWindow()) {
            (activity as AppCompatActivity).window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            actionBar?.hide()
        } else {
            (activity as AppCompatActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            actionBar?.show()
        }

        val viewDataBinding = DataBindingUtil.inflate<V>(inflater, getLayoutRes(), container, false)
        intViewBinding(viewDataBinding)
        return viewDataBinding.root
    }
}