package com.kalashnyk.denys.redditapp.repository.api

import com.kalashnyk.denys.redditapp.repository.response.ListingResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {
    @GET("/r/{subreddit}/top.json")
    fun getTop(
            @Path("subreddit") subreddit: String,
            @Query("limit") limit: Int): Single<Response<ListingResponse>>

    @GET("/r/{subreddit}/top.json")
    fun getTopAfter(
            @Path("subreddit") subreddit: String,
            @Query("after") after: String,
            @Query("limit") limit: Int): Single<Response<ListingResponse>>
}