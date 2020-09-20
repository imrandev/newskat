package com.imudev.newskat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imudev.newskat.utils.ViewType


abstract class BaseRecyclerAdapter<T, L : IBaseClickListener<T>?> protected constructor(
    private var itemList: MutableList<T>?,
    private var onItemClickedListener : L
) : RecyclerView.Adapter<BaseViewHolder<T, L>>() {

    private var onItemClickListener: L = this.onItemClickedListener

    private val differCallback = object : DiffUtil.ItemCallback<T>(){

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return areContentsTheSame(oldItem, newItem)
        }
    }

    init {
        if (itemList == null){
            itemList = ArrayList()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, L> {
        return if (itemList == null || itemList!!.size == 0) onCreateEmptyView(parent, viewType) else onCreateView(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, L>, position: Int) {
        if (itemList!!.size > 0 && getItemViewType(position) == ViewType.NORMAL_VIEW){
            val item = itemList!!.get(position)
            holder.onBindView(item, onItemClickListener)
        } else {
            holder.onBindView()
        }
    }

    override fun getItemCount(): Int {
        return if (itemList == null || itemList!!.size == 0) 1 else itemList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList == null || itemList!!.size == 0) ViewType.LOADER_VIEW else ViewType.NORMAL_VIEW
    }

    fun update(newItemList: MutableList<T>) {
        val diffResult =
            itemList?.let {
                DiffUtil.calculateDiff(object : DiffUtilItemCallback<T>(it, newItemList){
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return areSameItems(itemList?.get(oldItemPosition), itemList?.get(newItemPosition))
                    }

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return areSameContents(itemList?.get(oldItemPosition), itemList?.get(newItemPosition))
                    }
                })
            }

        this.itemList?.clear();
        this.itemList?.addAll(newItemList);
        diffResult?.dispatchUpdatesTo(this)
    }

    fun add(newItemList: List<T>?) {
        val startPosition = itemCount
        if (newItemList != null) {
            itemList!!.clear()
            itemList!!.addAll(newItemList)
        }
        notifyItemRangeInserted(startPosition, itemList!!.size)
    }

    fun remove(item: T) {
        val position = itemList!!.indexOf(item)
        if (itemList!!.remove(item)) notifyItemRemoved(position)
    }

    abstract fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<T, L>
    abstract fun onCreateEmptyView(parent: ViewGroup?, viewType: Int): BaseViewHolder<T, L>
    abstract fun areSameItems(oldItem: T?, newItem: T?): Boolean
    abstract fun areSameContents(oldItem: T?, newItem: T?): Boolean
}