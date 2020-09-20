package com.imudev.newskat.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import com.imudev.newskat.R
import com.imudev.newskat.databinding.FragmentSearchBinding
import com.imudev.newskat.ui.base.BaseFragment


class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_search;
    }

    override fun intViewBinding(viewBinding: FragmentSearchBinding) {
        fragmentSearchBinding = viewBinding;
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search news"
        searchItem.expandActionView();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(queryTextListener)
    }

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            return true
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return true
        }
    }
}