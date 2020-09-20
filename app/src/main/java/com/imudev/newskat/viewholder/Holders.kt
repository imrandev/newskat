package com.imudev.newskat.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.imudev.newskat.R
import com.imudev.newskat.adapter.BaseViewHolder
import com.imudev.newskat.adapter.IBaseClickListener
import com.imudev.newskat.databinding.*
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.model.source.Source
import com.imudev.newskat.utils.CalendarUtil
import com.imudev.newskat.utils.ColorUtil

class HeadlineViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<Article, IBaseClickListener<Article>>(viewDataBinding){

    private var itemTopHeadlineBinding = viewDataBinding as ItemTopHeadlineBinding


    companion object {
        private const val TAG = "Holders"
    }

    override fun onBindView(`object`: Article) {

    }

    override fun onBindView(`object`: Article, onItemClickedListener: IBaseClickListener<Article>) {

        itemTopHeadlineBinding.itemTvAuthorSource.text = `object`.source.name
        itemTopHeadlineBinding.itemTvDescription.text = `object`.description
        itemTopHeadlineBinding.itemTvDatetime.text = CalendarUtil.instance.comparableTime(`object`.publishedAt)
        itemTopHeadlineBinding.itemTvTitle.text = `object`.title

        Glide.with(itemView).asDrawable().load(`object`.urlToImage).override(
            itemTopHeadlineBinding.itemIvCover.measuredWidth,
            itemTopHeadlineBinding.itemIvCover.measuredHeight
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                itemTopHeadlineBinding.itemProgressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                itemTopHeadlineBinding.itemProgressBar.visibility = View.GONE
                return false
            }
        })
            .error(R.drawable.ic_online_articles).into(itemTopHeadlineBinding.itemIvCover)
        attachListener(onItemClickedListener, `object`)
    }

    override fun onViewRecycled() {}
    override fun onBindView() {

    }
}

class HeadlineSourceViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<Article, IBaseClickListener<Article>>(viewDataBinding){

    private var itemHeadlineBinding = viewDataBinding

    companion object {
        private const val TAG = "Holders"
    }

    init {

    }

    override fun onBindView(`object`: Article) {

    }

    override fun onBindView(`object`: Article, onItemClickedListener: IBaseClickListener<Article>) {
        if (itemHeadlineBinding is ItemSourceHeadlineAltBinding){
            init(itemHeadlineBinding as ItemSourceHeadlineAltBinding, `object`, onItemClickedListener)
        } else {
            init(itemHeadlineBinding as ItemSourceHeadlineBinding, `object`, onItemClickedListener)
        }
    }

    private fun init(itemHeadlineBinding: ItemSourceHeadlineAltBinding, `object`: Article, onItemClickedListener: IBaseClickListener<Article>) {
        itemHeadlineBinding.itemTvAuthorSource.text = `object`.source.name
        itemHeadlineBinding.itemTvDescription.text = `object`.description
        itemHeadlineBinding.itemTvDatetime.text = CalendarUtil.instance.comparableTime(`object`.publishedAt)
        itemHeadlineBinding.itemTvTitle.text = `object`.title

        Glide.with(itemView).asDrawable().load(`object`.urlToImage).override(
            itemHeadlineBinding.itemIvCover.measuredWidth,
            itemHeadlineBinding.itemIvCover.measuredHeight
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                itemHeadlineBinding.itemProgressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                itemHeadlineBinding.itemProgressBar.visibility = View.GONE
                return false
            }
        })
            .error(R.drawable.ic_online_articles).into(itemHeadlineBinding.itemIvCover)
        attachListener(onItemClickedListener, `object`)
    }

    private fun init(itemHeadlineBinding: ItemSourceHeadlineBinding, `object`: Article, onItemClickedListener: IBaseClickListener<Article>) {
        itemHeadlineBinding.itemTvAuthorSource.text = `object`.source.name
        itemHeadlineBinding.itemTvDescription.text = `object`.description
        itemHeadlineBinding.itemTvDatetime.text = CalendarUtil.instance.comparableTime(`object`.publishedAt)
        itemHeadlineBinding.itemTvTitle.text = `object`.title

        Glide.with(itemView).asDrawable().load(`object`.urlToImage).override(
            itemHeadlineBinding.itemIvCover.measuredWidth,
            itemHeadlineBinding.itemIvCover.measuredHeight
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                itemHeadlineBinding.itemProgressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                itemHeadlineBinding.itemProgressBar.visibility = View.GONE
                return false
            }
        })
            .error(R.drawable.ic_online_articles).into(itemHeadlineBinding.itemIvCover)
        attachListener(onItemClickedListener, `object`)
    }

    override fun onViewRecycled() {}
    override fun onBindView() {

    }
}

class SourceViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<Source, IBaseClickListener<Source>>(viewDataBinding){

    private var itemSourceBinding = viewDataBinding as ItemSourceBinding

    override fun onBindView(`object`: Source) {

    }

    override fun onBindView(`object`: Source, onItemClickedListener: IBaseClickListener<Source>) {
        `object`?.let {
            itemSourceBinding.itemTvName.text = it.name
            itemSourceBinding.itemTvDescription.text = it.description
            itemSourceBinding.ivSourceIcon.setColorFilter(ColorUtil.instance.generateRandomColor())

            val lp: ViewGroup.LayoutParams = itemSourceBinding.cardView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                val flexboxLp = lp
                flexboxLp.flexGrow = 1.0f
                flexboxLp.alignSelf = AlignItems.FLEX_END
            }
            attachListener(onItemClickedListener, `object`)
        }
    }

    override fun onViewRecycled() {

    }

    override fun onBindView() {

    }
}

class CategoryViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<String, IBaseClickListener<String>>(viewDataBinding){

    private var itemCategoryBinding = viewDataBinding as ItemCategoryBinding

    override fun onBindView(`object`: String) {

    }

    override fun onBindView(`object`: String, onItemClickedListener: IBaseClickListener<String>) {
        `object`?.let {
            itemCategoryBinding.itemTvCategory.text = it
        }
    }

    override fun onViewRecycled() {

    }

    override fun onBindView() {

    }
}

class HeadlineEmptyViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<Article, IBaseClickListener<Article>>(viewDataBinding){

    override fun onBindView(`object`: Article) {

    }

    override fun onBindView(`object`: Article, onItemClickedListener: IBaseClickListener<Article>) {

    }

    override fun onViewRecycled() {

    }

    override fun onBindView() {

    }
}

class SourceEmptyViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<Source, IBaseClickListener<Source>>(viewDataBinding){

    private var itemEmptyBinding = viewDataBinding as ItemEmptyBinding

    override fun onBindView(`object`: Source) {

    }

    override fun onBindView(`object`: Source, onItemClickedListener: IBaseClickListener<Source>) {

    }

    override fun onViewRecycled() {

    }

    override fun onBindView() {

    }
}

class CategoryEmptyViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<String, IBaseClickListener<String>>(viewDataBinding){

    override fun onBindView(`object`: String) {

    }

    override fun onBindView(`object`: String, onItemClickedListener: IBaseClickListener<String>) {

    }

    override fun onViewRecycled() {

    }

    override fun onBindView() {

    }
}