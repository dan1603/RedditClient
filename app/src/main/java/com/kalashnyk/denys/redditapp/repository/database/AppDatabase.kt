package com.kalashnyk.denys.redditapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalashnyk.denys.redditapp.repository.database.dao.RedditPostDao
import com.kalashnyk.denys.redditapp.repository.database.entity.RedditPostEntity

@Database(
        entities = [RedditPostEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): RedditPostDao
}