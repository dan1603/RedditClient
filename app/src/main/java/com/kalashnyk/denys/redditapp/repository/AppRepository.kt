package com.kalashnyk.denys.redditapp.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kalashnyk.denys.redditapp.repository.database.AppDatabase
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity
import com.kalashnyk.denys.redditapp.repository.response.ListingResponse
import com.kalashnyk.denys.redditapp.repository.server.ServerCommunicator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.Executors

class AppRepository(private val communicator: ServerCommunicator, private val db: AppDatabase) {

    var postAfter: String? = null
    var subreddit: String = "androiddev"

    private fun insertResultIntoDb(subredditName: String, body: ListingResponse?) {
        try {
            body!!.data.children.let { posts ->
                db.runInTransaction {
                    val start = db.postDao().getNextIndexInSubreddit(subredditName)
                    val items = posts.mapIndexed { index, child ->
                        child.data.indexInResponse = start + index
                        child.data
                    }
                    db.postDao().insert(items)
                }
            }
        } catch (ex: Exception) {
            Log.d("AppRepository", "" + ex.message)
        }

    }

    @SuppressLint("CheckResult")
    private fun getTop(subreddit: String, pageSize: Int) {
        communicator.getTop(subreddit, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful) {
                        insertResultIntoDb(subreddit, response.body())
                        postAfter = response.body()?.data?.after
                    }
                }
    }

    @SuppressLint("CheckResult")
    private fun getTopAfrer(subreddit: String, after: String, pageSize: Int) {
        communicator.getTopAfrer(subreddit, after, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful) {
                        insertResultIntoDb(subreddit, response.body())
                    }
                }
    }

    fun postsOfSubreddit(subReddit: String, pageSize: Int): LiveData<PagedList<RedditPostEntity>> {

        this.subreddit = subReddit

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize)
                .setPageSize(pageSize).build()

        val executor = Executors.newFixedThreadPool(3)
        val livePagedListBuilder = LivePagedListBuilder(db.postDao().postsBySubreddit(subReddit), pagedListConfig)

        if (postAfter == null) {
            getTop(subReddit, pageSize)
        } else {
            getTopAfrer(subReddit, postAfter!!, pageSize)
        }

        return livePagedListBuilder
                .setFetchExecutor(executor)
                .build()
    }

}
