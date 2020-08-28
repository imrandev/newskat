package com.imudev.newskat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, L : IBaseClickListener<T>?>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun attachListener(onItemClickListener: L, item: T) {
        itemView.setOnClickListener { view: View? ->
            onItemClickListener!!.onItemClicked(
                view,
                item,
                adapterPosition
            )
        }
    }

    protected fun attachListenerWithCustomView(
        view: View,
        onItemClickListener: L,
        item: T
    ) {
        view.setOnClickListener { view1 ->
            onItemClickListener!!.onItemClicked(
                view1,
                item,
                adapterPosition
            )
        }
    }
}