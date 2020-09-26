package com.imudev.newskat.ui.headline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.imudev.newskat.R
import com.imudev.newskat.adapter.BaseRecyclerAdapter
import com.imudev.newskat.adapter.BaseViewHolder
import com.imudev.newskat.adapter.IBaseClickListener
import com.imudev.newskat.databinding.FragmentHeadlineBinding
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.ui.base.BaseFragment
import com.imudev.newskat.utils.ConstantUtil
import com.imudev.newskat.utils.WrapperLinearLayoutManager
import com.imudev.newskat.viewholder.HeadlineEmptyViewHolder
import com.imudev.newskat.viewholder.HeadlineViewHolder

class HeadlineFragment : BaseFragment<FragmentHeadlineBinding>() {

    private lateinit var fragmentHeadlineBinding: FragmentHeadlineBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val baseRecyclerAdapter = object : BaseRecyclerAdapter<Article, IBaseClickListener<Article>>(null, headlineItemClickListener){
            override fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<Article, IBaseClickListener<Article>> {
                return HeadlineViewHolder(DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent?.context), R.layout.item_top_headline, parent, false))
            }

            override fun areSameItems(oldItem: Article?, newItem: Article?): Boolean {
                return oldItem?.url == newItem?.url
            }

            override fun areSameContents(oldItem: Article?, newItem: Article?): Boolean {
                return oldItem == newItem
            }

            override fun onCreateEmptyView(
                parent: ViewGroup?,
                viewType: Int
            ): BaseViewHolder<Article, IBaseClickListener<Article>> {
                return HeadlineEmptyViewHolder(DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent?.context), R.layout.item_empty, parent, false))
            }
        }
        baseRecyclerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        fragmentHeadlineBinding.rvHeadlines.apply {
            layoutManager = WrapperLinearLayoutManager(context)
            adapter = baseRecyclerAdapter
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.findHeadlines().observe(viewLifecycleOwner, Observer {
                // this is the trigger point when headline data has changed
                baseRecyclerAdapter.update(it.toMutableList())
            })
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_headline
    }

    override fun intViewBinding(viewBinding: FragmentHeadlineBinding) {
        fragmentHeadlineBinding = viewBinding;
    }

    val headlineItemClickListener = object : IBaseClickListener<Article> {
        override fun onItemClicked(view: View?, item: Article, position: Int) {
            val extras = Bundle()
            extras.putInt("position", position)
            extras.putString(ConstantUtil.API_TAG, ConstantUtil.API_HEADLINE)
            val navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
            navController.navigate(R.id.action_headlineFragment_to_articleFragment, extras)
        }
    }
}