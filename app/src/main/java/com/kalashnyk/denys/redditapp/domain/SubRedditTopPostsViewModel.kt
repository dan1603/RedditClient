package com.kalashnyk.denys.redditapp.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kalashnyk.denys.redditapp.repository.AppRepository

class SubRedditTopPostsViewModel(private val repository: AppRepository) : ViewModel() {

    private val subredditName = MutableLiveData<String>()

    private val repoResult = Transformations.map(subredditName) {
        repository.postsOfSubreddit(it, 10)
    }

    val posts = Transformations.switchMap(repoResult) { it }!!

    fun showSubReddit(subreddit: String): Boolean {
        if (subredditName.value == subreddit) {
            return false
        }
        subredditName.value = subreddit
        return true
    }

    fun currentSubreddit(): String? = subredditName.value
}