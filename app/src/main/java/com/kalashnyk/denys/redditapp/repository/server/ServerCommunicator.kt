package com.kalashnyk.denys.redditapp.repository.server

import com.kalashnyk.denys.redditapp.repository.api.RedditApi
import com.kalashnyk.denys.redditapp.repository.response.ListingResponse
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ServerCommunicator(private val apiService: RedditApi) {

    companion object {
        private const val DEFAULT_TIMEOUT = 10
        private const val DEFAULT_RETRY_ATTEMPTS: Long = 4
    }

    private fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .retry(DEFAULT_RETRY_ATTEMPTS)
    }

    fun getTop(subreddit: String, limit: Int): Single<Response<ListingResponse>> {
        return apiService.getTop(subreddit, limit).compose(singleTransformer())
    }

    fun getTopAfrer(subreddit: String, after: String, limit: Int): Single<Response<ListingResponse>> {
        return apiService.getTopAfter(subreddit, after, limit).compose(singleTransformer())
    }
}
