package com.imudev.newskat.ui.article

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.imudev.newskat.R
import com.imudev.newskat.adapter.BaseRecyclerAdapter
import com.imudev.newskat.adapter.BaseViewHolder
import com.imudev.newskat.adapter.IBaseClickListener
import com.imudev.newskat.databinding.FragmentArticleListBinding
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.ui.base.BaseFragment
import com.imudev.newskat.ui.main.MainActivity
import com.imudev.newskat.utils.ConstantUtil
import com.imudev.newskat.utils.WrapperFlexLayoutManager
import com.imudev.newskat.viewholder.HeadlineEmptyViewHolder
import com.imudev.newskat.viewholder.HeadlineSourceViewHolder

class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    private var source: String? = null
    private lateinit var fragmentArticleListBinding: FragmentArticleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            source = it.getString("source", "")
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_article_list
    }

    override fun intViewBinding(viewBinding: FragmentArticleListBinding) {
        viewBinding.let {
            fragmentArticleListBinding = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.activityMainBinding.appbar.toolbar.setNavigationOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            navController.navigateUp()
        }

        val baseRecyclerAdapter = object : BaseRecyclerAdapter<Article, IBaseClickListener<Article>>(null, headlineItemClickListener){
            override fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<Article, IBaseClickListener<Article>> {
                return HeadlineSourceViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(parent?.context), R.layout.item_source_headline_alt, parent, false))
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
                return HeadlineEmptyViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(parent?.context), R.layout.item_empty, parent, false))
            }
        }
        baseRecyclerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val flexboxLayoutManager = context?.let {
            WrapperFlexLayoutManager(it).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
        }

        fragmentArticleListBinding.rvArticleList.apply {
            layoutManager = flexboxLayoutManager
            adapter = baseRecyclerAdapter
        }

        lifecycleScope.launchWhenStarted {
            source?.let {
                mainActivity.supportActionBar?.title = source
                mainViewModel.findHeadlinesBySourceId(it).observe(viewLifecycleOwner, Observer { headlines ->
                    baseRecyclerAdapter.update(headlines.toMutableList())
                })
            }
        }
    }

    val headlineItemClickListener = object : IBaseClickListener<Article> {
        override fun onItemClicked(view: View?, item: Article, position: Int) {
            val extras = Bundle()
            extras.putInt("position", position)
            extras.putString(ConstantUtil.API_TAG, ConstantUtil.API_HEADLINE_BY_SOURCE)
            extras.putString(ConstantUtil.API_SOURCES, source)
            val navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
            navController.navigate(R.id.action_articleListFragment_to_articleFragment, extras)
        }
    }
}