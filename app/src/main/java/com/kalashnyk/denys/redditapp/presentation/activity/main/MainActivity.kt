package com.kalashnyk.denys.redditapp.presentation.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity
import com.kalashnyk.denys.redditapp.repository.network.NetworkState
import com.kalashnyk.denys.redditapp.domain.SubRedditTopPostsViewModel
import com.kalashnyk.denys.redditapp.presentation.adapter.PostsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import com.kalashnyk.denys.redditapp.R
import com.kalashnyk.denys.redditapp.databinding.ActivityMainBinding
import com.kalashnyk.denys.redditapp.utils.AppHelper
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_SUBREDDIT = "subreddit"
        const val DEFAULT_SUBREDDIT = "androiddev"
    }

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: SubRedditTopPostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppHelper.injectDependency(this)
        initAdapter()
        val subreddit = savedInstanceState?.getString(KEY_SUBREDDIT) ?: DEFAULT_SUBREDDIT
        viewModel.showSubReddit(subreddit)
    }

    private fun initAdapter() {
        val linearLayoutManager = GridLayoutManager(this, 1)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        val adapter = PostsAdapter()

        binding.root.rvPostsList.layoutManager = linearLayoutManager
        binding.root.rvPostsList.adapter = adapter
        viewModel.posts.observeForever { adapter.submitList(it) }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SUBREDDIT, viewModel.currentSubreddit())
    }

}
