package com.imudev.newskat.ui.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.imudev.newskat.R
import com.imudev.newskat.databinding.FragmentWebBinding
import com.imudev.newskat.ui.base.BaseFragment
import com.imudev.newskat.ui.main.MainActivity
import com.imudev.newskat.utils.ConstantUtil

@SuppressLint("SetJavaScriptEnabled")
class WebFragment : BaseFragment<FragmentWebBinding>() {

    private lateinit var fragmentWebBinding: FragmentWebBinding
    private lateinit var articleUri : String
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            articleUri = it.getString(ConstantUtil.ARTICLE_URI, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        toolbar = mainActivity.activityMainBinding.appbar.toolbar;
        toolbar.setNavigationOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            navController.navigateUp()
        }
        fragmentWebBinding.articleWebView.apply {
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(true)
                loadWithOverviewMode = true
                useWideViewPort = true
                domStorageEnabled = true
            }
            webChromeClient = WebChromeClient()
            webViewClient = ArticleWebViewClient()
        }
        fragmentWebBinding.articleWebView.loadUrl(articleUri)
        WebView.setWebContentsDebuggingEnabled(false)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_web
    }

    override fun intViewBinding(viewBinding: FragmentWebBinding) {
        this.fragmentWebBinding = viewBinding;
    }

    private inner class ArticleWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
            view.loadUrl(request?.url.toString())
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            fragmentWebBinding.progressBar.visibility = View.GONE
            toolbar.title = view.title
        }
    }
}