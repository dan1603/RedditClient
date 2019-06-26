package com.kalashnyk.denys.redditapp.presentation.item

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.kalashnyk.denys.redditapp.R
import com.kalashnyk.denys.redditapp.databinding.RedditPostItemBinding
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity

class RedditPostViewHolder(private val binding: RedditPostItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private var post : RedditPostEntity? = null

    init {
        binding.root.setOnClickListener {
            post?.url?.let { url ->
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(binding.root.resources.getColor(R.color.colorPrimary))
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(binding.root.context, Uri.parse(url))
            }
        }
    }

    fun bind(post: RedditPostEntity?) {
        this.post = post
        binding.post = this.post
    }

    companion object {
        fun create(parent: ViewGroup): RedditPostViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RedditPostItemBinding.inflate(inflater)
            return RedditPostViewHolder(binding)
        }
    }

    fun updateScore(item: RedditPostEntity?) {
        post = item
        binding.post = this.post
    }

}