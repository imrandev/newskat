package com.imudev.newskat.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager

class WrapperLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    constructor(context: Context, orientation: Int, reverseLayout : Boolean) : this(context) {

    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.d(Companion.TAG, "onLayoutChildren: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "CustomViews"
    }
}

class WrapperFlexLayoutManager(context: Context) : FlexboxLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.d(Companion.TAG, "onLayoutChildren: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "CustomViews"
    }
}