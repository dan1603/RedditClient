package com.kalashnyk.denys.redditapp.presentation.adapter

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.kalashnyk.denys.redditapp.R
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity
import com.kalashnyk.denys.redditapp.repository.network.NetworkState
import com.kalashnyk.denys.redditapp.presentation.item.NetworkStateItemViewHolder
import com.kalashnyk.denys.redditapp.presentation.item.RedditPostViewHolder

class PostsAdapter() : PagedListAdapter<RedditPostEntity, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.reddit_post_item -> (holder as RedditPostViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            (holder as RedditPostViewHolder).updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.reddit_post_item -> RedditPostViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.reddit_post_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditPostEntity>() {
            override fun areContentsTheSame(oldItem: RedditPostEntity, newItem: RedditPostEntity): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: RedditPostEntity, newItem: RedditPostEntity): Boolean =
                    oldItem.name == newItem.name

            override fun getChangePayload(oldItem: RedditPostEntity, newItem: RedditPostEntity): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: RedditPostEntity, newItem: RedditPostEntity): Boolean {
            return oldItem.copy(score = newItem.score) == newItem
        }
    }
}
