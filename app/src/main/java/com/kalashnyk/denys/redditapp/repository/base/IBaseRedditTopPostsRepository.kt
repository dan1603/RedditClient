package com.kalashnyk.denys.redditapp.repository.base

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity

interface IBaseRedditTopPostsRepository {
    fun postsOfSubreddit(subReddit: String, pageSize: Int): LiveData<PagedList<RedditPostEntity>>
}