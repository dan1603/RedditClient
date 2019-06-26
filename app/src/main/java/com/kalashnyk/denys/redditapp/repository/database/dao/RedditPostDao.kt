package com.kalashnyk.denys.redditapp.repository.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity

@Dao
interface RedditPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<RedditPostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: RedditPostEntity)

    @Query("SELECT * FROM postDao WHERE subreddit = :subreddit ORDER BY indexInResponse ASC")
    fun postsBySubreddit(subreddit : String) : DataSource.Factory<Int, RedditPostEntity>

    @Query("DELETE FROM postDao WHERE subreddit = :subreddit")
    fun deleteBySubreddit(subreddit: String)

    @Query("SELECT MAX(indexInResponse) + 1 FROM postDao WHERE subreddit = :subreddit")
    fun getNextIndexInSubreddit(subreddit: String) : Int
}