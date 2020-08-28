package com.imudev.newskat.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intViewBinding(DataBindingUtil.setContentView(this, getLayoutRes()))
    }

    @LayoutRes
    protected abstract fun getLayoutRes() : Int

    protected abstract fun intViewBinding(viewBinding: V)
}