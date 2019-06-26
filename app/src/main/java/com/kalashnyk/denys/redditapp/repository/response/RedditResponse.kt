package com.kalashnyk.denys.redditapp.repository.response

import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity

class ListingResponse(val data: ListingData)

class ListingData(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?)

data class RedditChildrenResponse(val data: RedditPostEntity)