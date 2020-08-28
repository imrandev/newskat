package com.imudev.newskat.adapter

import android.content.res.Resources.NotFoundException
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T, L : IBaseClickListener<T>?, V : BaseViewHolder<T, L>?> protected constructor(
    private var itemList: MutableList<T>?
) :
    RecyclerView.Adapter<V>() {

    private var onItemClickListener: L? = null
    fun attachListener(onItemClickListener: L) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        return onCreateView(parent, viewType)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        val item = if (isRecyclerViewEmpty) null else itemList!![position]
        onBindView(holder, item, position)

        // attaching click listener to view
        onItemClickListener?.let {
            if (item != null) {
                if (holder != null) {
                    holder.attachListener(it, item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (itemList!!.size == 0) 1 else itemList!!.size
    }

    protected val isRecyclerViewEmpty: Boolean
        get() = itemList!!.size == 0

    fun update(newItemList: MutableList<T>?) {
        itemList = newItemList
        notifyItemRangeChanged(0, itemList!!.size)
    }

    fun add(newItemList: List<T>?) {
        val startPosition = itemCount
        if (newItemList != null) {
            itemList?.addAll(newItemList)
        }
        notifyItemRangeInserted(startPosition, itemList!!.size)
    }

    fun remove(item: T) {
        val position = itemList!!.indexOf(item)
        if (itemList!!.remove(item)) notifyItemRemoved(position)
    }

    abstract fun onCreateView(parent: ViewGroup?, viewType: Int): V
    abstract fun onBindView(holder: V, item: T?, position: Int)

    init {
        if (itemList == null) throw NotFoundException("Recycler adapter item list is null")
    }
}