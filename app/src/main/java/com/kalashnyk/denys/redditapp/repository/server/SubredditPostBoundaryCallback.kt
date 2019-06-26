//package com.kalashnyk.denys.redditapp.repository.server
//
//import androidx.paging.PagedList
//import androidx.annotation.MainThread
//import com.kalashnyk.denys.redditapp.repository.api.RedditApi
//import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.util.concurrent.Executor
//import com.kalashnyk.denys.redditapp.utils.PagingRequestHelper
//import com.kalashnyk.denys.redditapp.repository.network.createStatusLiveData
//import com.kalashnyk.denys.redditapp.repository.response.ListingResponse
//
//class SubredditPostBoundaryCallback(
//        private val subredditName: String,
//        private val webservice: ServerCommunicator,
//        private val handleResponse: (String, ListingResponse?) -> Unit,
//        private val ioExecutor: Executor,
//        private val networkPageSize: Int)
//    : PagedList.BoundaryCallback<RedditPostEntity>() {
//
//    val helper = PagingRequestHelper(ioExecutor)
//    val networkState = helper.createStatusLiveData()
//
//    @MainThread
//    override fun onZeroItemsLoaded() {
//        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
//            webservice.getTop(
//                    subreddit = subredditName,
//                    limit = networkPageSize)
//                    .enqueue(createWebserviceCallback(it))
//        }
//    }
//
//    @MainThread
//    override fun onItemAtEndLoaded(itemAtEnd: RedditPostEntity) {
//        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
//            webservice.getTopAfter(
//                    subreddit = subredditName,
//                    after = itemAtEnd.name,
//                    limit = networkPageSize)
//                    .enqueue(createWebserviceCallback(it))
//        }
//    }
//
//    private fun insertItemsIntoDb(
//            response: Response<ListingResponse>,
//            it: PagingRequestHelper.Request.Callback) {
//        ioExecutor.execute {
//            handleResponse(subredditName, response.body())
//            it.recordSuccess()
//        }
//    }
//
//    override fun onItemAtFrontLoaded(itemAtFront: RedditPostEntity) { }
//
//    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback)
//            : Callback<ListingResponse> {
//        return object : Callback<ListingResponse> {
//            override fun onFailure(call: Call<ListingResponse>, t: Throwable) {
//                it.recordFailure(t)
//            }
//
//            override fun onResponse(
//                    call: Call<ListingResponse>,
//                    response: Response<ListingResponse>) {
//                insertItemsIntoDb(response, it)
//            }
//        }
//    }
//}