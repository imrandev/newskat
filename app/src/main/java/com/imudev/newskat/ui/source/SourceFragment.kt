package com.imudev.newskat.ui.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.imudev.newskat.R
import com.imudev.newskat.adapter.BaseRecyclerAdapter
import com.imudev.newskat.adapter.BaseViewHolder
import com.imudev.newskat.adapter.IBaseClickListener
import com.imudev.newskat.databinding.FragmentSourceBinding
import com.imudev.newskat.model.source.Source
import com.imudev.newskat.ui.base.BaseFragment
import com.imudev.newskat.utils.WrapperFlexLayoutManager
import com.imudev.newskat.viewholder.CategoryEmptyViewHolder
import com.imudev.newskat.viewholder.CategoryViewHolder
import com.imudev.newskat.viewholder.SourceEmptyViewHolder
import com.imudev.newskat.viewholder.SourceViewHolder

class SourceFragment : BaseFragment<FragmentSourceBinding>() {

    private lateinit var fragmentSourceBinding: FragmentSourceBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = object : BaseRecyclerAdapter<String, IBaseClickListener<String>>(mainViewModel.getCategories(), categoryItemClickListener){
            override fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<String, IBaseClickListener<String>> {
                return CategoryViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(parent?.context), R.layout.item_category, parent, false))
            }

            override fun areSameItems(oldItem: String?, newItem: String?): Boolean {
                return oldItem == newItem
            }

            override fun areSameContents(oldItem: String?, newItem: String?): Boolean {
                return oldItem == newItem
            }

            override fun onCreateEmptyView(
                parent: ViewGroup?,
                viewType: Int
            ): BaseViewHolder<String, IBaseClickListener<String>> {
                return CategoryEmptyViewHolder(DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent?.context), R.layout.item_category, parent, false))
            }
        }
        categoryAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        fragmentSourceBinding.rvCategories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        val flexboxLayoutManager = context?.let {
            WrapperFlexLayoutManager(it).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
        }

        val baseRecyclerAdapter = object : BaseRecyclerAdapter<Source, IBaseClickListener<Source>>(null, sourceItemClickListener){
            override fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<Source, IBaseClickListener<Source>> {
                return SourceViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent?.context), R.layout.item_source, parent, false))
            }

            override fun areSameItems(oldItem: Source?, newItem: Source?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areSameContents(oldItem: Source?, newItem: Source?): Boolean {
                return oldItem == newItem
            }

            override fun onCreateEmptyView(parent: ViewGroup?, viewType: Int): BaseViewHolder<Source, IBaseClickListener<Source>> {
                return SourceEmptyViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(parent?.context), R.layout.item_empty, parent, false))
            }
        }
        baseRecyclerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        fragmentSourceBinding.rvSources.apply {
            layoutManager = flexboxLayoutManager
            adapter = baseRecyclerAdapter
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.initSourcesApi("").observe(viewLifecycleOwner, Observer {
                baseRecyclerAdapter.update(it.toMutableList())
            })
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_source
    }

    override fun intViewBinding(viewBinding: FragmentSourceBinding) {
        fragmentSourceBinding = viewBinding;
    }

    val sourceItemClickListener = object : IBaseClickListener<Source> {
        override fun onItemClicked(view: View?, item: Source, position: Int) {
            val extras = Bundle()
            extras.putString("source", item.id)
            val navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
            navController.navigate(R.id.action_sourceFragment_to_articleListFragment, extras)
        }
    }

    val categoryItemClickListener = object : IBaseClickListener<String> {
        override fun onItemClicked(view: View?, item: String, position: Int) {

        }
    }
}