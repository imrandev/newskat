package com.imudev.newskat.ui.article

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.imudev.newskat.R
import com.imudev.newskat.adapter.BaseRecyclerAdapter
import com.imudev.newskat.adapter.BaseViewHolder
import com.imudev.newskat.adapter.IBaseClickListener
import com.imudev.newskat.databinding.FragmentArticleBinding
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.model.headline.Author
import com.imudev.newskat.ui.base.BaseFragment
import com.imudev.newskat.utils.CalendarUtil
import com.imudev.newskat.viewholder.HeadlineEmptyViewHolder
import com.imudev.newskat.viewholder.HeadlineSourceViewHolder

class ArticleFragment : BaseFragment<FragmentArticleBinding>() {

    private lateinit var fragmentArticleBinding: FragmentArticleBinding
    private var position = 0

    override fun isFullWindow(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_article;
    }

    override fun intViewBinding(viewBinding: FragmentArticleBinding) {
        fragmentArticleBinding = viewBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentArticleBinding.articleToolbar.setNavigationOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            navController.navigateUp()
        }

        val baseRecyclerAdapter = object : BaseRecyclerAdapter<Article, IBaseClickListener<Article>>(null, headlineItemClickListener){
            override fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<Article, IBaseClickListener<Article>> {
                return HeadlineSourceViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(parent?.context), R.layout.item_source_headline, parent, false))
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

        fragmentArticleBinding.rvMoreSourceHeadlines.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = baseRecyclerAdapter
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.initHeadline().observe(viewLifecycleOwner,
                Observer {
                    it?.get(position)?.let { headline -> setArticleIntoViews(headline, baseRecyclerAdapter) }
                }
            )
        }

    }

    private fun setArticleIntoViews(
        article: Article,
        baseRecyclerAdapter: BaseRecyclerAdapter<Article, IBaseClickListener<Article>>
    ) {

        Glide.with(fragmentArticleBinding.root).asDrawable().load(article.urlToImage).override(
            fragmentArticleBinding.ivNewsCover.measuredWidth,
            fragmentArticleBinding.ivNewsCover.measuredHeight
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                fragmentArticleBinding.progressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                fragmentArticleBinding.progressBar.visibility = View.GONE
                return false
            }
        })
            .error(R.drawable.ic_online_articles).into(fragmentArticleBinding.ivNewsCover)

        article.author?.let {
            if (it.contains("[{")){
                val author = Gson().fromJson<Author>(it, Author::class.java);
                if (author != null && !author.isEmpty()){
                    fragmentArticleBinding.articleToolbar.title = author.get(0).name
                }
            } else {
                fragmentArticleBinding.articleToolbar.title =
                    if (it.isEmpty()) getString(R.string.app_name) else it
            }
        }

        fragmentArticleBinding.tvTitle.text = article.title
        article.source?.let {
            fragmentArticleBinding.tvAuthorSource.text = it.name
            fragmentArticleBinding.tvAuthorSourceMore.text = String.format("More from ${it.name}")
        }
        fragmentArticleBinding.tvDatetime.text = CalendarUtil.instance.convertTimeToLocal(article.publishedAt)
        fragmentArticleBinding.tvDescription.text = article.description

        article.content?.let {
            fragmentArticleBinding.tvContent.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentArticleBinding.tvContent.text = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
            } else {
                fragmentArticleBinding.tvContent.text = Html.fromHtml(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.initHeadlinesBySource(article.source).observe(viewLifecycleOwner, Observer {
                baseRecyclerAdapter.update(it.toMutableList())
                Handler().postDelayed({
                    fragmentArticleBinding.rvMoreSourceHeadlines.visibility = View.VISIBLE
                }, 1000)
            })
        }
    }

    companion object {
        private const val TAG = "ArticleFragment"
    }

    val headlineItemClickListener = object : IBaseClickListener<Article> {
        override fun onItemClicked(view: View?, item: Article, position: Int) {
            val navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
        }
    }
}